package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.ProfilDTO;
import com.msyconseil.bokkobackapi.dto.UserDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.UserStatusEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.UserException;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.utils.Utils;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService<UserDTO, UserModel> implements IService<UserModel, UserDTO>, ICRUDService<UserDTO, String> {

    @Autowired
    UserRepository userRepository;

    private final SessionService sessionService;

    @Autowired
    public UserService(SessionService sessionService) {
        super(sessionService);
        this.sessionService = sessionService;
    }

    @Transactional
    public UserDTO add(UserDTO userDTO) throws ErrorException {
        if (userDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        UserModel userModel = generateEntityByDTO(userDTO);
        userModel.setPassword(Utils.hash(userDTO.getPassword()));
        userModel = userRepository.save(userModel);
        return generateDTOByEntity(userModel);
    }

    public CustomAnswer<UserDTO> update(final Map<String, String> headers, UserDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        if (headers.get("token") == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
        CustomAnswer<UserDTO> response = new CustomAnswer<UserDTO>();
        try {
            getActiveSession(headers);
            UserModel entity = getUserByEmail(email);
            if (entity != null) {
                updateInformation(entity, parameter);
                entity = userRepository.save(entity);
                UserDTO userDTO = generateDTOByEntity(entity);
                userDTO.setToken(headers.get("token"));
                userDTO.setId(entity.getId());
                response.setContent(userDTO);
            } else {
                throw new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS);
            }
        } catch (ErrorException e) {
            e.fillInStackTrace();
            response.setErrorMessage(ErrorMessageEnum.TOKEN_EXPIRED.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<UserDTO> delete(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        if (headers.get("token") == null || headers.get("token").isEmpty()) throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
        CustomAnswer<UserDTO> response = new CustomAnswer<>();

        try {
            getActiveSession(headers);
            UserModel entity = getUserByEmail(email);

            // Si getUserByEmail ne lance pas d'exception, l'utilisateur existe et on peut continuer
            entity.setStatut("I");
            UserModel updatedEntity = userRepository.save(entity);

            UserDTO userDTO = generateDTOByEntity(updatedEntity);
            userDTO.setToken(headers.get("token"));
            userDTO.setId(updatedEntity.getId());
            response.setContent(userDTO);
        } catch (ErrorException e) {
            // Gestion de l'exception si l'utilisateur n'est pas trouvé
            throw new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS);
        } catch (Exception e) {
            // Gestion d'autres exceptions potentielles
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    public UserDTO add(UserModel userModel) throws Exception {
        return generateDTOByEntity(userRepository.save(userModel));
    }

    @Override
    public CustomAnswer<UserDTO> get(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        if (headers.get("token") == null || headers.get("token").isEmpty()){
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        CustomAnswer<UserDTO> response = new CustomAnswer<>();

        // Validation de la session. Si invalide, une ErrorException est levée.
        try {
            getActiveSession(headers);

            // Supposons que getUserByEmail(email) pourrait retourner null si l'utilisateur n'est pas trouvé.
            UserModel entity = getUserByEmail(email); // Utilisation directe de l'email fourni au lieu de celui de la sessionModel
            if (entity == null) {
                // Si aucun utilisateur n'est trouvé, plutôt que de retourner un objet vide,
                // lever une exception indiquant que l'utilisateur n'est pas trouvé.
                throw new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS);
            }
            // Conversion de l'entité UserModel en DTO.
            UserDTO userDTO = generateDTOByEntity(entity);
            userDTO.setToken(headers.get("token"));

            // Préparation et retour de la réponse.
            response.setContent(userDTO);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return response;
    }


    @Override
    public CustomListAnswer<List<UserDTO>> getAll(final Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        if (headers.get("token") == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
        }
        CustomListAnswer<List<UserDTO>> response = new CustomListAnswer<>();
        try {
            getActiveSession(headers);

            // Utilisation de PageRequest pour paginer les résultats de userRepository.findAll()
            List<UserDTO> list = new LinkedList<>();
            for (UserModel userModel : userRepository.findAll()) {
                list.add(generateDTOByEntity(userModel));
            }
            if (list.isEmpty()) {
                throw new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS);
            } else {
                response.setContent(list);
            }
        } catch (ErrorException e) {
            e.fillInStackTrace();
            response.setErrorMessage(ErrorMessageEnum.TOKEN_EXPIRED.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }


    @Override
    public CustomAnswer<UserDTO> add(final Map<String, String> headers, UserDTO userDTO) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        getActiveSession(headers);
        CustomAnswer<UserDTO> response = new CustomAnswer<>();
        try {
            UserDTO dto = add(userDTO);
            response.setContent(dto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public UserModel getUserByEmail(String email){
        return userRepository.findByEmailAndActive(email);
    }


    private void updateInformation(UserModel userModel, UserDTO dto){
        if (dto.isPasswordIsChange()){
            userModel.setPassword(Utils.hash(dto.getPassword()));
        }
        userModel.setName(dto.getName());
        userModel.setFirstName(dto.getFirstName());
        userModel.setEmail(dto.getEmail());
        userModel.setPhoneNumber(dto.getPhoneNumber());
        userModel.setStatut(dto.getStatut().getMessage());
    }

    public CustomAnswer<UserDTO> login(UserDTO dto) throws Exception {
        CustomAnswer<UserDTO> response = new CustomAnswer<UserDTO>();
        try {
            if (dto.getEmail() == null || dto.getPassword() == null)
                throw new Exception("Identifiant ou mot de passe manquant");
            UserModel optional = getUserByEmail(dto.getEmail());
            if (optional != null) {
                // Supposons que Utils.hash() hash le mot de passe fourni et que Utils.compare() compare les deux hash.
                boolean isGood = Utils.compare(dto.getPassword(), optional.getPassword());
                if (isGood) {
                    UserDTO userDTO = generateDTOByEntity(optional);
                    userDTO.setToken(sessionService.add(optional).getToken());
                    userDTO.setId(optional.getId());
                    response.setContent(userDTO);
                } else {
                    response.setErrorMessage("Identifiant ou mot de passe incorrect");
                }
            } else {
                response.setErrorMessage("Utilisateur non trouvé");
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public CustomAnswer<Boolean> logout(final Map<String, String> headers) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<Boolean> response = new CustomAnswer<Boolean>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            response.setContent(sessionService.destroySession(sessionModel.getToken()));
        } catch (Exception e){
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public UserModel generateEntityByDTO(UserDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public UserModel mapDTOToEntity(UserDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        UserModel entity = new UserModel();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setFirstName(dto.getFirstName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatut(dto.getStatut().getMessage());
        entity.setDateInscription(dto.getDateInscription());
        return entity;
    }

    @Override
    public UserModel mapEntityByWithDTO(UserModel entity, UserDTO dto) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setFirstName(dto.getFirstName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatut(dto.getStatut().getMessage());
        entity.setDateInscription(dto.getDateInscription());
        return entity;
    }

    @Override
    public UserDTO generateDTOByEntity(UserModel entity) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFirstName(entity.getFirstName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhoneNumber(entity.getPhoneNumber());
        if (Objects.equals(entity.getStatut(), "B")) {
            dto.setStatut(UserStatusEnum.BLOCKED);
        } else if (Objects.equals(entity.getStatut(), "I")) {
            dto.setStatut(UserStatusEnum.INACTIF);
        } else {
            dto.setStatut(UserStatusEnum.ACTIF);
        }
        dto.setDateInscription(entity.getDateInscription());
        return dto;
    }
}
