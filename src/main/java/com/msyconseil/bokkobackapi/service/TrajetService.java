package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.TrajetDTO;
import com.msyconseil.bokkobackapi.model.TrajetModel;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.repository.TrajetRepository;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.TrajetException;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
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
public class TrajetService extends AbstractService<TrajetDTO, TrajetModel> implements IService<TrajetModel, TrajetDTO>, ICRUDService<TrajetDTO, String> {

    @Autowired
    TrajetRepository trajetRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public TrajetService(SessionService sessionService) {
        super(sessionService);
    }

    public UserModel getUserByEmail(String email){
        return userRepository.findByEmailAndActive(email);
    }


    private void updateInformation(TrajetModel trajetModel, TrajetDTO trajetDTO) {
        trajetModel.setDepart(trajetDTO.getDepart());
        trajetModel.setArrivee(trajetDTO.getArrivee());
        trajetModel.setDateHeureDepart(trajetDTO.getDateHeureDepart());
        trajetModel.setNbPlaces(trajetDTO.getNbPlaces());
        trajetModel.setPrix(trajetDTO.getPrix());
        trajetModel.setPrix(trajetDTO.getPrix());
        trajetModel.setStatut(trajetDTO.getStatut());
    }

    @Override
    public TrajetModel generateEntityByDTO(TrajetDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public TrajetModel mapEntityByWithDTO(TrajetModel entity, TrajetDTO dto) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setDepart(dto.getDepart());
        entity.setArrivee(dto.getArrivee());
        entity.setDateHeureDepart(dto.getDateHeureDepart());
        entity.setNbPlaces(dto.getNbPlaces());
        entity.setPrix(dto.getPrix());
        entity.setStatut(dto.getStatut());
        return entity;
    }

    @Override
    public TrajetDTO generateDTOByEntity(TrajetModel entity) throws ErrorException {
        TrajetDTO trajetDTO = new TrajetDTO();
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        trajetDTO.setUserDTO(userService.generateDTOByEntity(entity.getUserModel()));
        trajetDTO.setDepart(entity.getDepart());
        trajetDTO.setArrivee(entity.getArrivee());
        trajetDTO.setDateHeureDepart(entity.getDateHeureDepart());
        trajetDTO.setNbPlaces(entity.getNbPlaces());
        trajetDTO.setPrix(entity.getPrix());
        trajetDTO.setStatut(entity.getStatut());
        return trajetDTO;
    }

    @Override
    public TrajetModel mapDTOToEntity(TrajetDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        TrajetModel trajetModel = new TrajetModel();
        trajetModel.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        trajetModel.setDepart(dto.getDepart());
        trajetModel.setArrivee(dto.getArrivee());
        trajetModel.setDateHeureDepart(dto.getDateHeureDepart());
        trajetModel.setNbPlaces(dto.getNbPlaces());
        trajetModel.setPrix(dto.getPrix());
        trajetModel.setStatut(dto.getStatut());
        return trajetModel;
    }

    @Override
    public CustomAnswer<TrajetDTO> get(Map<String, String> headers, String email) throws Exception {

        return null;
    }

    @Override
    public CustomListAnswer<List<TrajetDTO>> getAll(Map<String, String> headers, int page, int size) throws Exception {
        return null;
    }

    @Override
    public CustomAnswer<TrajetDTO> add(Map<String, String> headers, TrajetDTO parameter) throws Exception {
        return null;
    }

    @Override
    public CustomAnswer<TrajetDTO> update(Map<String, String> headers, TrajetDTO parameter, String email) throws Exception {
        return null;
    }

    @Override
    public CustomAnswer<TrajetDTO> delete(Map<String, String> headers, String email) throws Exception {
        return null;
    }
}
