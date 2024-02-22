package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.UserDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService implements IService<UserModel, UserDTO> {

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
        if (userDTO == null) throw new ErrorException(ErrorMessageEnum.ENTITY_CREATION_ERROR);
        UserModel userModel = generateEntityByDTO(userDTO);
        userModel.setPassword(Utils.hash(userDTO.getPassword()));
        userModel = userRepository.save(userModel);
        return generateDTOByEntity(userModel);
    }

    public CustomAnswer<UserDTO> update(Map<String, String> headers, UserDTO parameter, String email) throws ErrorException {
        CustomAnswer<UserDTO> response = new CustomAnswer<UserDTO>();
        SessionModel sessionModel = getActiveSession(headers);
        UserModel entity = getUserByEmail(email);
        updateInformation(entity, parameter);
        entity = userRepository.save(entity);
        if (entity != null) {
            UserDTO userDTO = generateDTOByEntity(entity);
            response.setContent(userDTO);
        }
        return response;
    }

    public CustomAnswer<UserDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        CustomAnswer<UserDTO> response = new CustomAnswer<UserDTO>();
        SessionModel sessionModel = getActiveSession(headers);
        UserModel entity = getUserByEmail(email);
        entity.setStatut("I");
        entity = userRepository.save(entity);
        if (entity != null) {
            UserDTO userDTO = generateDTOByEntity(entity);
            response.setContent(userDTO);
            return response;
        }
        return new CustomAnswer<UserDTO>();
    }

    public UserDTO add(UserModel userModel) throws Exception {
        return generateDTOByEntity(userRepository.save(userModel));
    }

    public CustomAnswer<UserDTO> add(final Map<String, String> headers, UserDTO userDTO) throws ErrorException, Exception {
        if (headers == null) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        SessionModel sessionModel = getActiveSession(headers);

        CustomAnswer<UserDTO> response = new CustomAnswer<>();
        try {
            UserDTO dto = add(userDTO);
            response.setContent(dto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public UserModel getUserByEmail(String email){
        return userRepository.findByEmailAndActive(email);
    }

    public UserDTO get(final Map<String, String> headers, String email) throws Exception {
        getActiveSession(headers);
        return generateDTOByEntity(getUserByEmail(email));
    }

    private void updateInformation(UserModel userModel, UserDTO dto){
        if (dto.isPasswordIsChange()){
            userModel.setPassword(Utils.hash(dto.getPassword()));
        }
        userModel.setName(dto.getName());
        userModel.setFirstName(dto.getFirstName());
        userModel.setEmail(dto.getEmail());
        userModel.setPhoneNumber(dto.getPhoneNumber());
        userModel.setStatut(dto.getStatut());
    }

    public UserDTO edit(final Map<String, String> headers, UserDTO dto) throws Exception{
        SessionModel sessionModel = getActiveSession(headers);
        UserModel entity = getUserByEmail(dto.getEmail());

        updateInformation(entity, dto);

        entity = userRepository.save(entity);
        if (entity != null) return generateDTOByEntity(entity);
        return new UserDTO();
    }

    public CustomAnswer<Boolean> logout(final Map<String, String> headers){
        CustomAnswer<Boolean> response = new CustomAnswer<Boolean>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            response.setContent(sessionService.destroySession(sessionModel.getToken()));
        } catch (Exception e){
            e.printStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public List<UserDTO> getAll(final Map<String, String> headers) throws Exception {
        SessionModel sessionModel = getActiveSession(headers);
        return userRepository.findAll().parallelStream().map(userModel -> {
            try {
                return this.generateDTOByEntity(userModel);
            } catch (ErrorException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public UserModel generateEntityByDTO(UserDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    public UserModel mapDTOToEntity(UserDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        UserModel entity = new UserModel();
        entity.setName(dto.getName());
        entity.setFirstName(dto.getFirstName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatut(dto.getStatut());
        return entity;
    }

    @Override
    public UserModel mapEntityByWithDTO(UserModel entity, UserDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public UserDTO generateDTOByEntity(UserModel entity) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        UserDTO dto = new UserDTO();
        dto.setName(entity.getName());
        dto.setFirstName(entity.getFirstName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setStatut(entity.getStatut());
        return dto;
    }
}
