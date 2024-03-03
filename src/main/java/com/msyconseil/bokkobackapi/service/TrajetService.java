package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.TrajetDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.TrajetModel;
import com.msyconseil.bokkobackapi.repository.TrajetRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.TrajetMessageEnum;
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
import java.util.Objects;

@Service
public class TrajetService extends AbstractService<TrajetDTO, TrajetModel> implements IService<TrajetModel, TrajetDTO>, ICRUDService<TrajetDTO, String> {

    @Autowired
    TrajetRepository trajetRepository;

    @Autowired
    UserService userService;

    public TrajetService(SessionService sessionService) {
        super(sessionService);
    }

    @Transactional
    public TrajetDTO add(TrajetDTO trajetDTO) throws ErrorException {
        if (trajetDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        TrajetModel trajetModel = generateEntityByDTO(trajetDTO);
        trajetModel = trajetRepository.save(trajetModel);
        return generateDTOByEntity(trajetModel);
    }

    public TrajetDTO add(TrajetModel trajetModel) throws ErrorException {
        return generateDTOByEntity(trajetRepository.save(trajetModel));
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

    private List<TrajetModel> getAllTrajets() {
        return trajetRepository.findAllTrajet();
    }

    private TrajetModel getLastTrajetByDriver(int idConducteur) {
        return trajetRepository.findLastTrajetByConducteur(idConducteur);
    }

    private List<TrajetModel> getAllTrajetByDriver(int idConducteur) {
        return trajetRepository.findAllByDriver(idConducteur);
    }

    private TrajetModel getTrajetById(int idTrajet) {
        return trajetRepository.findById(idTrajet);
    }

    private List<TrajetModel> getAllTrajetByStatutToBecome() {
        return trajetRepository.findAllByStatutToBecome();
    }

    private List<TrajetModel> getAllTrajetByStatutInProgress() {
        return trajetRepository.findAllByStatutInProgress();
    }

    private List<TrajetModel> getAllTrajetByStatutFinished() {
        return trajetRepository.findAllByStatutFinished();
    }

    private List<TrajetModel> getAllTrajetByStatutCanceled() {
        return trajetRepository.findAllByStatutCanceled();
    }

    private List<TrajetModel> getAllTrajetByStatutToBecomeForDriver(int idConducteur) {
        return trajetRepository.findAllByStatutToBecomeForDriver(idConducteur);
    }

    private List<TrajetModel> getAllTrajetByStatutInProgressForDriver(int idConducteur) {
        return trajetRepository.findAllByStatutInProgressForDriver(idConducteur);
    }

    private List<TrajetModel> getAllTrajetByStatutFinishedForDriver(int idConducteur) {
        return trajetRepository.findAllByStatutFinishedForDriver(idConducteur);
    }

    private List<TrajetModel> getAllTrajetByStatutCanceledForDriver(int idConducteur) {
        return trajetRepository.findAllByStatutCanceledForDriver(idConducteur);
    }

    private TrajetModel getLastTrajetByStatutToBecomeForDriver(int idConducteur) {
        return trajetRepository.findLastTrajetByStatutToBecomeForDriver(idConducteur);
    }

    private TrajetModel getLastTrajetByStatutInProgressForDriver(int idConducteur) {
        return trajetRepository.findLastTrajetByStatutInProgressForDriver(idConducteur);
    }

    private TrajetModel getLastTrajetByStatutFinishedForDriver(int idConducteur) {
        return trajetRepository.findLastTrajetByStatutFinishedForDriver(idConducteur);
    }

    private TrajetModel getLastTrajetByStatutCanceledForDriver(int idConducteur) {
        return trajetRepository.findLastTrajetByStatutCanceledForDriver(idConducteur);
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
    public CustomAnswer<TrajetDTO> get(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<TrajetDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            TrajetModel trajetModel = getLastTrajetByDriver(sessionModel.getUserModel().getId());
            if (trajetModel != null) {
                TrajetDTO trajetDTO = generateDTOByEntity(trajetModel);
                response.setContent(trajetDTO);
            } else {
                throw new TrajetException(TrajetMessageEnum.NOT_FOUND);
            }
        }catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomListAnswer<List<TrajetDTO>> getAll(final Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<TrajetDTO>> response = new CustomListAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            List<TrajetDTO> list = new LinkedList<TrajetDTO>();
            for (TrajetModel trajetModel : trajetRepository.findAllTrajet()) {
                list.add(generateDTOByEntity(trajetModel));
            }
            if (list.isEmpty()) {
                throw new TrajetException(TrajetMessageEnum.NOT_FOUND);
            } else {
                response.setContent(list);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<TrajetDTO> add(final Map<String, String> headers, TrajetDTO parameter) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<TrajetDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            TrajetDTO trajetDTO = add(parameter);
            if (trajetDTO == null) throw new TrajetException(TrajetMessageEnum.ERROR_TRAJET_CREATION);
            response.setContent(trajetDTO);
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<TrajetDTO> update(final Map<String, String> headers, TrajetDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<TrajetDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            TrajetModel trajetModel = getLastTrajetByDriver(sessionModel.getUserModel().getId());
            if (trajetModel != null) {
                updateInformation(trajetModel, parameter);
                trajetModel = trajetRepository.save(trajetModel);
                TrajetDTO trajetDTO = generateDTOByEntity(trajetModel);
                response.setContent(trajetDTO);
            } else {
                throw new TrajetException(TrajetMessageEnum.NOT_FOUND);
            }
        } catch (TrajetException e) {
            e.fillInStackTrace();
            response.setErrorMessage(TrajetMessageEnum.ERROR_TRAJET_UPDATE.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<TrajetDTO> delete(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<TrajetDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(email, sessionModel.getUserModel().getEmail())) {
                TrajetModel trajetModel = getLastTrajetByDriver(sessionModel.getUserModel().getId());
                if (trajetModel != null) {
                    trajetRepository.delete(trajetModel);
                    TrajetDTO trajetDTO = generateDTOByEntity(trajetModel);
                    response.setContent(trajetDTO);
                } else {
                    throw new TrajetException(TrajetMessageEnum.ERROR_TRAJET_DELETE);
                }
            } else {
                throw new Exception("Le token n'appartient pas à cette utilisateur...");
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
