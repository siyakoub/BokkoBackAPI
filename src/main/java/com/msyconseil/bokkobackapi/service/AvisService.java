package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.AvisDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.AvisModel;
import com.msyconseil.bokkobackapi.repository.AvisRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.AvisMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.AvisException;
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
public class AvisService extends AbstractService<AvisDTO, AvisModel> implements IService<AvisModel, AvisDTO>, ICRUDService<AvisDTO, String>{

    @Autowired
    AvisRepository avisRepository;

    @Autowired
    UserService userService;

    @Autowired
    ReservationService reservationService;

    public AvisService(SessionService sessionService) {
        super(sessionService);
    }

    private List<AvisModel> getAllAvisByUserAndReservation(int idUser, int idReservation) {
        return avisRepository.findAllByReservationAndUser(idReservation, idUser);
    }

    private List<AvisModel> getAllAvis() {
        return avisRepository.findAllAvis();
    }

    private List<AvisModel> getAllAvisByReservation(int idReservation) {
        return avisRepository.findAllAvisByReservation(idReservation);
    }

    private List<AvisModel> getAllAvisByUser(int idUser) {
        return avisRepository.findAllAvisByUser(idUser);
    }

    private AvisModel getAvisById(int id) {
        return avisRepository.findAvisById(id);
    }

    private AvisModel getLastAvisByReservation(int idReservation) {
        return avisRepository.findLastAvisByReservation(idReservation);
    }

    private AvisModel getLastAvisByUser(int idUser) {
        return avisRepository.findLastAvisByUser(idUser);
    }

    private AvisModel getLastAvisByUserAndReservation(int idUser, int idReservation) {
        return avisRepository.findLastAvisByReservationAndUser(idReservation, idUser);
    }

    @Transactional
    public AvisDTO add(AvisDTO avisDTO) throws ErrorException {
        if (avisDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        AvisModel avisModel = generateEntityByDTO(avisDTO);
        avisModel = avisRepository.save(avisModel);
        return generateDTOByEntity(avisModel);
    }

    public AvisDTO add(AvisModel avisModel) throws ErrorException {
        return generateDTOByEntity(avisRepository.save(avisModel));
    }

    @Override
    public CustomAnswer<AvisDTO> get(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<AvisDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(email, sessionModel.getUserModel().getEmail())) {
                AvisModel avisModel = getLastAvisByUser(sessionModel.getUserModel().getId());
                if (avisModel != null) {
                    AvisDTO avisDTO = generateDTOByEntity(avisModel);
                    response.setContent(avisDTO);
                } else {
                    throw new AvisException(AvisMessageEnum.REVIEW_NOT_FOUND);
                }
            } else {
                throw new Exception("Le token n'appartient pas à cette utilisateur...");
            }
        } catch (AvisException e) {
            e.fillInStackTrace();
            response.setErrorMessage(AvisMessageEnum.REVIEW_NOT_FOUND.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return null;
    }

    @Override
    public CustomListAnswer<List<AvisDTO>> getAll(final Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<AvisDTO>> response = new CustomListAnswer<>();
        try {
            getActiveSession(headers);
            List<AvisDTO> list = new LinkedList<>();
            for (AvisModel avisModel : avisRepository.findAllAvis()) {
                list.add(generateDTOByEntity(avisModel));
            }
            if (list.isEmpty()) {
                throw new AvisException(AvisMessageEnum.REVIEW_NOT_FOUND);
            } else {
                response.setContent(list);
            }
        } catch (AvisException e) {
            e.fillInStackTrace();
            response.setErrorMessage(AvisMessageEnum.REVIEW_NOT_FOUND.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<AvisDTO> add(final Map<String, String> headers, AvisDTO parameter) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<AvisDTO> response = new CustomAnswer<>();
        try {
            AvisDTO dto = add(parameter);
            if (dto == null) throw new AvisException(AvisMessageEnum.ERROR_CREATION_AVIS);
            response.setContent(dto);
        } catch (AvisException e) {
            e.fillInStackTrace();
            response.setErrorMessage(AvisMessageEnum.ERROR_CREATION_AVIS.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<AvisDTO> update(final Map<String, String> headers, AvisDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<AvisDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            AvisModel avisModel = getLastAvisByUserAndReservation(sessionModel.getUserModel().getId(), parameter.getReservationDTO().getId());
            if (avisModel != null) {
                updateInformation(avisModel, parameter);
                avisModel = avisRepository.save(avisModel);
                AvisDTO avisDTO = generateDTOByEntity(avisModel);
                response.setContent(avisDTO);
            } else {
                throw new AvisException(AvisMessageEnum.ERROR_UPDATE_AVIS);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<AvisDTO> delete(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<AvisDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(email, sessionModel.getUserModel().getEmail())) {
                AvisModel avisModel = getLastAvisByUser(sessionModel.getUserModel().getId());
                if (avisModel != null) {
                    avisRepository.delete(avisModel);
                    AvisDTO dto = generateDTOByEntity(avisModel);
                    response.setContent(dto);
                }else {
                    throw new AvisException(AvisMessageEnum.ERROR_DELETING_AVIS);
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

    private void updateInformation(AvisModel avisModel, AvisDTO avisDTO) {
        avisModel.setNote(avisDTO.getNote());
        avisModel.setCommentaire(avisDTO.getCommentaire());
        avisModel.setDateHeureAvis(avisDTO.getDateHeureAvis());
    }

    @Override
    public AvisModel generateEntityByDTO(AvisDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public AvisModel mapEntityByWithDTO(AvisModel entity, AvisDTO dto) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setReservationModel(reservationService.generateEntityByDTO(dto.getReservationDTO()));
        entity.setNote(dto.getNote());
        entity.setCommentaire(dto.getCommentaire());
        entity.setDateHeureAvis(dto.getDateHeureAvis());
        return entity;
    }

    @Override
    public AvisDTO generateDTOByEntity(AvisModel entity) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        AvisDTO avisDTO = new AvisDTO();
        avisDTO.setUserDTO(userService.generateDTOByEntity(entity.getUserModel()));
        avisDTO.setReservationDTO(reservationService.generateDTOByEntity(entity.getReservationModel()));
        avisDTO.setNote(entity.getNote());
        avisDTO.setCommentaire(entity.getCommentaire());
        avisDTO.setDateHeureAvis(entity.getDateHeureAvis());
        return avisDTO;
    }

    @Override
    public AvisModel mapDTOToEntity(AvisDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        AvisModel avisModel = new AvisModel();
        avisModel.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        avisModel.setReservationModel(reservationService.generateEntityByDTO(dto.getReservationDTO()));
        avisModel.setNote(dto.getNote());
        avisModel.setCommentaire(dto.getCommentaire());
        avisModel.setDateHeureAvis(dto.getDateHeureAvis());
        return avisModel;
    }
}
