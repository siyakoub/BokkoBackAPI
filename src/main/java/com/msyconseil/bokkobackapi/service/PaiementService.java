package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.PaiementDTO;
import com.msyconseil.bokkobackapi.model.ReservationModel;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.PaiementModel;
import com.msyconseil.bokkobackapi.repository.PaiementRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.PaiementMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.PaiementException;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import com.msyconseil.bokkobackapi.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

@Service
public class PaiementService extends AbstractService<PaiementDTO, PaiementModel> implements IService<PaiementModel, PaiementDTO>, ICRUDService<PaiementDTO, String> {

    @Autowired
    UserService userService;

    @Autowired
    PaiementRepository paiementRepository;

    @Autowired
    ReservationService reservationService;

    public PaiementService(SessionService sessionService) {
        super(sessionService);
    }

    private PaiementModel getLastPaiementByUser(int idUser) {
        return paiementRepository.findLastPaiementByUser(idUser);
    }

    private List<PaiementModel> getAllPaiementByUser(int idUser) {
        return paiementRepository.findAllPaiementByUser(idUser);
    }

    @Transactional
    public PaiementDTO add(PaiementDTO paiementDTO) throws ErrorException {
        if (paiementDTO == null) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        PaiementModel paiementModel = generateEntityByDTO(paiementDTO);
        paiementModel = paiementRepository.save(paiementModel);
        return generateDTOByEntity(paiementModel);
    }

    public PaiementDTO add(PaiementModel paiementModel) throws ErrorException {
        return generateDTOByEntity(paiementRepository.save(paiementModel));
    }

    @Override
    public CustomAnswer<PaiementDTO> get(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<PaiementDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            PaiementModel paiementModel = getLastPaiementByUser(sessionModel.getUserModel().getId());
            if (paiementModel != null) {
                PaiementDTO paiementDTO = generateDTOByEntity(paiementModel);
                response.setContent(paiementDTO);
            } else {
                throw new PaiementException(PaiementMessageEnum.PAYMENT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomListAnswer<List<PaiementDTO>> getAll(final Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        CustomListAnswer<List<PaiementDTO>> response = new CustomListAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            List<PaiementDTO> list = new LinkedList<>();
            for (PaiementModel paiementModel : getAllPaiementByUser(sessionModel.getUserModel().getId())){
                list.add(generateDTOByEntity(paiementModel));
            }
            if (list.isEmpty()) {
                throw new PaiementException(PaiementMessageEnum.PAYMENT_NOT_FOUND);
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
    public CustomAnswer<PaiementDTO> add(final Map<String, String> headers, PaiementDTO parameter) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<PaiementDTO> response = new CustomAnswer<>();
        try {
            getActiveSession(headers);
            PaiementDTO dto = add(parameter);
            response.setContent(dto);
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<PaiementDTO> update(final Map<String, String> headers, PaiementDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<PaiementDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            PaiementModel paiementModel = getLastPaiementByUser(sessionModel.getUserModel().getId());
            if (paiementModel != null) {
                updateInformation(paiementModel, parameter);
                paiementModel = paiementRepository.save(paiementModel);
                PaiementDTO paiementDTO = generateDTOByEntity(paiementModel);
                response.setContent(paiementDTO);
            } else {
                throw new PaiementException(PaiementMessageEnum.PAYMENT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<PaiementDTO> delete(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<PaiementDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            PaiementModel paiementModel = getLastPaiementByUser(sessionModel.getUserModel().getId());
            if (paiementModel != null) {
                paiementRepository.deleteById(paiementModel.getId());
                PaiementDTO paiementDTO = generateDTOByEntity(paiementModel);
                response.setContent(paiementDTO);
            } else {
                throw new PaiementException(PaiementMessageEnum.PAYMENT_NOT_FOUND);
            }
        } catch (PaiementException e) {
            e.fillInStackTrace();
            response.setErrorMessage(PaiementMessageEnum.ERROR_PAYMENT_DELETE.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public void updateInformation(PaiementModel paiementModel, PaiementDTO paiementDTO) {
        paiementModel.setMethode(paiementDTO.getMethode());
        paiementModel.setMontant(paiementDTO.getMontant());
        paiementModel.setDateHeurePaiement(LocalDateTime.now());
        paiementModel.setStatut(paiementDTO.getStatut());
    }

    @Override
    public PaiementModel generateEntityByDTO(PaiementDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public PaiementModel mapEntityByWithDTO(PaiementModel entity, PaiementDTO dto) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        entity.setReservationModel(reservationService.generateEntityByDTO(dto.getReservationDTO()));
        entity.setMethode(dto.getMethode());
        entity.setMontant(dto.getMontant());
        entity.setDateHeurePaiement(dto.getDateHeurePaiement());
        entity.setStatut(dto.getStatut());
        return entity;
    }

    @Override
    public PaiementDTO generateDTOByEntity(PaiementModel entity) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        PaiementDTO paiementDTO = new PaiementDTO();
        paiementDTO.setReservationDTO(reservationService.generateDTOByEntity(entity.getReservationModel()));
        paiementDTO.setMontant(entity.getMontant());
        paiementDTO.setMethode(entity.getMethode());
        paiementDTO.setDateHeurePaiement(entity.getDateHeurePaiement());
        paiementDTO.setStatut(entity.getStatut());
        return paiementDTO;
    }

    @Override
    public PaiementModel mapDTOToEntity(PaiementDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        PaiementModel entity = new PaiementModel();
        entity.setReservationModel(reservationService.generateEntityByDTO(dto.getReservationDTO()));
        entity.setMethode(dto.getMethode());
        entity.setMontant(dto.getMontant());
        entity.setDateHeurePaiement(dto.getDateHeurePaiement());
        entity.setStatut(dto.getStatut());
        return entity;
    }
}
