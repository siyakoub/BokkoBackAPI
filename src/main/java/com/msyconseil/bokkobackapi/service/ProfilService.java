package com.msyconseil.bokkobackapi.service;


import com.msyconseil.bokkobackapi.dto.ProfilDTO;
import com.msyconseil.bokkobackapi.model.ProfilModel;
import com.msyconseil.bokkobackapi.repository.ProfilRepository;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.interf.IService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilService extends AbstractService implements IService<ProfilModel, ProfilDTO> {

    @Autowired
    ProfilRepository profilRepository;

    @Autowired
    UserService userService;

    private final SessionService sessionService;

    @Transactional
    public ProfilDTO add(ProfilDTO profilDTO) throws ErrorException {
        if (profilDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        return null;
    }


    public ProfilService(SessionService sessionService) {
        super(sessionService);
        this.sessionService = sessionService;
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

    public ProfilModel mapDTOToEntity(ProfilDTO profilDTO) throws ErrorException {
        if (profilDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        ProfilModel entity = new ProfilModel();
        entity.setUserModel(userService.generateEntityByDTO(profilDTO.getUserDTO()));
        entity.setBio(profilDTO.getBio());
        entity.setPictureProfil(profilDTO.getPictureProfil());
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
