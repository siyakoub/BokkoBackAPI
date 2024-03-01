package com.msyconseil.bokkobackapi.service;


import com.msyconseil.bokkobackapi.dto.ProfilDTO;
import com.msyconseil.bokkobackapi.dto.ProfilRegisterDTO;
import com.msyconseil.bokkobackapi.model.ProfilModel;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.repository.ProfilRepository;
import com.msyconseil.bokkobackapi.repository.SessionRepository;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.ProfilMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.ProfilException;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import com.msyconseil.bokkobackapi.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfilService extends AbstractService<ProfilDTO, ProfilModel> implements IService<ProfilModel, ProfilDTO>, ICRUDService<ProfilDTO, String> {

    @Autowired
    ProfilRepository profilRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Transactional
    public ProfilDTO add(ProfilDTO profilDTO) throws ErrorException {
        if (profilDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        ProfilModel profilModel = generateEntityByDTO(profilDTO);
        profilModel = profilRepository.save(profilModel);
        return generateDTOByEntity(profilModel);
    }
    
    public ProfilDTO add(ProfilModel profilModel) throws ErrorException {
        return generateDTOByEntity(profilRepository.save(profilModel));
    }

    @Override
    public CustomAnswer<ProfilDTO> get(Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ProfilDTO> response = new CustomAnswer<>();
        try{
            SessionModel sessionModel = getActiveSession(headers);
            ProfilModel profilModel = getProfilByUser(sessionModel.getUserModel().getId());
            if (profilModel != null) {
                ProfilDTO profilDTO = generateDTOByEntity(profilModel);
                response.setContent(profilDTO);
            }else {
                throw new ProfilException(ProfilMessageEnum.PROFIL_NOT_FOUND);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomListAnswer<List<ProfilDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        CustomListAnswer<List<ProfilDTO>> response = new CustomListAnswer<>();
        try {
            getActiveSession(headers);
            List<ProfilDTO> list = new LinkedList<ProfilDTO>();
            for (ProfilModel profilModel : profilRepository.findAll()){
                list.add(generateDTOByEntity(profilModel));
            }
            if (list.isEmpty()) {
                throw new ProfilException(ProfilMessageEnum.PROFIL_NOT_FOUND);
            }else {
                response.setContent(list);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<ProfilDTO> add(final Map<String, String> headers, ProfilDTO profilDTO) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        getActiveSession(headers);
        CustomAnswer<ProfilDTO> response = new CustomAnswer<ProfilDTO>();
        try {
            ProfilDTO dto = add(profilDTO);
            if (dto == null) throw new ProfilException(ProfilMessageEnum.ERROR_PROFIL_CREATION);
            response.setContent(dto);
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<ProfilDTO> update(Map<String, String> headers, ProfilDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ProfilDTO> response = new CustomAnswer<ProfilDTO>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            ProfilModel profilModel = getProfilByUser(sessionModel.getUserModel().getId());
            updateInformation(profilModel, parameter);
            profilModel = profilRepository.save(profilModel);
            if (profilModel != null) {
                ProfilDTO profilDTO1 = generateDTOByEntity(profilModel);
                response.setContent(profilDTO1);
            } else {
                throw new ProfilException(ProfilMessageEnum.ERROR_PROFIL_CREATION);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public UserModel getUserByEmail(String email){
        return userRepository.findByEmailAndActive(email);
    }

    @Override
    public CustomAnswer<ProfilDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ProfilDTO> response = new CustomAnswer<>();
        try {
            getActiveSession(headers);
            UserModel userModel = getUserByEmail(email);
            ProfilModel profilModel = getProfilByUser(userModel.getId());
            if (profilModel != null) {
                profilRepository.deleteProfilByUserId(profilModel.getUserModel().getId());
                ProfilDTO profilDTO = generateDTOByEntity(profilModel);
                response.setContent(profilDTO);
            } else {
                throw new ProfilException(ProfilMessageEnum.ERROR_PROFIL_DELETE);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public ProfilModel getProfilByUser(int id) {
        return profilRepository.findProfilByUser(id);
    }

    public ProfilService(SessionService sessionService){
        super(sessionService);
    }


    public CustomAnswer<ProfilRegisterDTO> register(ProfilRegisterDTO profilRegisterDTO) {
        CustomAnswer<ProfilRegisterDTO> response = new CustomAnswer<>();
        try {
            if (
                    (profilRegisterDTO.getName() == null || profilRegisterDTO.getName().isEmpty())
                    || (profilRegisterDTO.getFirstName() == null || profilRegisterDTO.getFirstName().isEmpty())
                    || (profilRegisterDTO.getEmail() == null || profilRegisterDTO.getEmail().isEmpty())
                    || (profilRegisterDTO.getPassword() == null || profilRegisterDTO.getPassword().isEmpty())
                    || (profilRegisterDTO.getTelephone() == null || profilRegisterDTO.getTelephone().isEmpty())
                    || (profilRegisterDTO.getStatut() == null || profilRegisterDTO.getStatut().isEmpty())
                    || (profilRegisterDTO.getBio() == null || profilRegisterDTO.getBio().isEmpty())
                    || (profilRegisterDTO.getPhoto() == null || profilRegisterDTO.getPhoto().isEmpty())
            ) {
                response.setErrorMessage(ErrorMessageEnum.INVALID_TOKEN.name());
                return response;
            }
            profilRegisterDTO.setPassword(Utils.hash(profilRegisterDTO.getPassword()));
            List<SqlStoredProcedureAnswer> list = profilRepository.register(
                    profilRegisterDTO.getName(),
                    profilRegisterDTO.getFirstName(),
                    profilRegisterDTO.getEmail(),
                    profilRegisterDTO.getPassword(),
                    profilRegisterDTO.getTelephone(),
                    profilRegisterDTO.getStatut(),
                    profilRegisterDTO.getBio(),
                    profilRegisterDTO.getPhoto()
            );
            if (list.get(0).getStatus().equalsIgnoreCase("OK")) {
                response.setContent(profilRegisterDTO);
            } else {
                response.setErrorMessage(ErrorMessageEnum.USER_CREATION_ERROR.name());
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    private void updateInformation(ProfilModel profilModel, ProfilDTO profilDTO) {
        profilModel.setBio(profilDTO.getBio());
        profilModel.setPictureProfil(profilDTO.getPictureProfil());
    }

    @Override
    public ProfilModel generateEntityByDTO(ProfilDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public ProfilModel mapEntityByWithDTO(ProfilModel entity, ProfilDTO dto) throws ErrorException {
        if (entity == null){
            throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        }
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setBio(dto.getBio());
        entity.setPictureProfil(dto.getPictureProfil());
        return entity;
    }

    @Override
    public ProfilModel mapDTOToEntity(ProfilDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        ProfilModel entity = new ProfilModel();
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setBio(dto.getBio());
        entity.setPictureProfil(dto.getPictureProfil());
        return entity;
    }

    @Override
    public ProfilDTO generateDTOByEntity(ProfilModel entity) throws ErrorException {
        ProfilDTO profilDTO = new ProfilDTO();
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        profilDTO.setUserDTO(userService.generateDTOByEntity(entity.getUserModel()));
        profilDTO.setBio(entity.getBio());
        profilDTO.setPictureProfil(entity.getPictureProfil());
        return profilDTO;
    }
}
