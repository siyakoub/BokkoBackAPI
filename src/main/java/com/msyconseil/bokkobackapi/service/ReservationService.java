package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.ReservationDTO;
import com.msyconseil.bokkobackapi.dto.TrajetDTO;
import com.msyconseil.bokkobackapi.model.ReservationModel;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.TrajetModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.repository.ReservationRepository;
import com.msyconseil.bokkobackapi.repository.TrajetRepository;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.ReservationMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.TrajetMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.ReservationException;
import com.msyconseil.bokkobackapi.service.exception.TrajetException;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import com.msyconseil.bokkobackapi.service.interf.IService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.projection.EntityProjection.ProjectionType.DTO;

@Service
public class ReservationService extends AbstractService<ReservationDTO, ReservationModel> implements IService<ReservationModel, ReservationDTO>, ICRUDService<ReservationDTO, String> {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    TrajetService trajetService;


    public ReservationService(SessionService sessionService) {
        super(sessionService);
    }

    @Transactional
    public ReservationDTO add(ReservationDTO reservationDTO) throws ErrorException {
        if (reservationDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        ReservationModel reservationModel = generateEntityByDTO(reservationDTO);
        reservationModel = reservationRepository.save(reservationModel);
        return generateDTOByEntity(reservationModel);
    }

    public ReservationDTO add(ReservationModel reservationModel) throws ErrorException {
        return generateDTOByEntity(reservationRepository.save(reservationModel));
    }

    private ReservationModel getReservationByPassagerUserAndTrajet(int idTrajet, int idPassager) {
        return reservationRepository.findReservationByPassagerAndTrajet(idPassager, idTrajet);
    }

    private List<ReservationModel> getAllReservationByPassager(int idPassager) {
        return reservationRepository.findAllReservationByPassager(idPassager);
    }

    private List<ReservationModel> getAllReservationByTrajet(int idTrajet) {
        return reservationRepository.findByAllByTrajet(idTrajet);
    }

    private List<ReservationModel> getAllReservation() {
        return reservationRepository.findAllReservation();
    }

    private List<ReservationModel> getAllReservationInProgress() {
        return reservationRepository.findReservationByStatutInProgress();
    }

    private List<ReservationModel> getAllReservationConfirmed() {
        return reservationRepository.findReservationByStatutInConfirmation();
    }

    private List<ReservationModel> getAllReservationFinished() {
        return reservationRepository.findReservationByStatutFinished();
    }

    private ReservationModel getById(int id) {
        return reservationRepository.findById(id);
    }

    private ReservationModel getLastReservationInProgressByPassager(int idPassager) {
        return reservationRepository.findLastReservationInProgressByPassager(idPassager);
    }

    public ReservationModel getLastReservationConfirmedByPassager(int idPassager) {
        return reservationRepository.findLastReservationConfirmedByPassager(idPassager);
    }

    public ReservationModel getLastReservationFinishedByPassager(int idPassager) {
        return reservationRepository.findLastReservationFinishedByPassager(idPassager);
    }

    private ReservationModel getLastReservationInProgressByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationInProgressByTrajet(idTrajet);
    }

    private ReservationModel getLastReservationConfirmedByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationConfirmedByTrajet(idTrajet);
    }

    private ReservationModel getLastReservationFinishedByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationFinishedByTrajet(idTrajet);
    }

    private ReservationModel getLastReservationByPassager(int idPassager) {
        return reservationRepository.findLastReservationByPassager(idPassager);
    }

    private ReservationModel getLastReservationByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationByTrajet(idTrajet);
    }

    @Override
    public CustomAnswer<ReservationDTO> get(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ReservationDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            ReservationModel reservationModel = getLastReservationByPassager(sessionModel.getUserModel().getId());
            if (reservationModel != null) {
                ReservationDTO reservationDTO = generateDTOByEntity(reservationModel);
                response.setContent(reservationDTO);
            } else {
                throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomListAnswer<List<ReservationDTO>> getAll(final Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<ReservationDTO>> response = new CustomListAnswer<>();
        try {
            getActiveSession(headers);
            List<ReservationDTO> list = new LinkedList<ReservationDTO>();
            for (ReservationModel reservationModel : reservationRepository.findAll()){
                list.add(generateDTOByEntity(reservationModel));
            }
            if (list.isEmpty()) {
                throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
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
    public CustomAnswer<ReservationDTO> add(final Map<String, String> headers, ReservationDTO parameter) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ReservationDTO> response = new CustomAnswer<>();
        try {
            getActiveSession(headers);
            ReservationDTO dto = add(parameter);
            response.setContent(dto);
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<ReservationDTO> update(final Map<String, String> headers, ReservationDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ReservationDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            ReservationModel reservationModel = getLastReservationByPassager(sessionModel.getUserModel().getId());
            updateInformation(reservationModel, parameter);
            reservationModel = reservationRepository.save(reservationModel);
            if (reservationModel != null) {
                ReservationDTO reservationDTO = generateDTOByEntity(reservationModel);
                response.setContent(reservationDTO);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<ReservationDTO> delete(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ReservationDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            ReservationModel reservationModel = getLastReservationByPassager(sessionModel.getUserModel().getId());
            if (reservationModel != null) {
                reservationRepository.deleteById(reservationModel.getId());
                ReservationDTO reservationDTO = generateDTOByEntity(reservationModel);
                response.setContent(reservationDTO);
            } else {
                throw new ReservationException(ReservationMessageEnum.ERROR_RESERVATION_DELETE);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    private void updateInformation(ReservationModel reservationModel, ReservationDTO reservationDTO) {
        reservationModel.setNbPlaceReserv(reservationDTO.getNbPlacesReserv());
        reservationModel.setDateReservation(reservationDTO.getDateReservation());
        reservationModel.setStatut(reservationDTO.getStatut());
    }

    @Override
    public ReservationModel generateEntityByDTO(ReservationDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public ReservationModel mapEntityByWithDTO(ReservationModel entity, ReservationDTO dto) throws ErrorException {
        if (entity == null) {
            throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        }
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setTrajetModel(trajetService.generateEntityByDTO(dto.getTrajetDTO()));
        entity.setNbPlaceReserv(dto.getNbPlacesReserv());
        entity.setDateReservation(dto.getDateReservation());
        entity.setStatut(dto.getStatut());
        return entity;
    }

    @Override
    public ReservationDTO generateDTOByEntity(ReservationModel entity) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setTrajetDTO(trajetService.generateDTOByEntity(entity.getTrajetModel()));
        reservationDTO.setUserDTO(userService.generateDTOByEntity(entity.getUserModel()));
        reservationDTO.setNbPlacesReserv(entity.getNbPlaceReserv());
        reservationDTO.setDateReservation(entity.getDateReservation());
        reservationDTO.setStatut(entity.getStatut());
        return reservationDTO;
    }

    @Override
    public ReservationModel mapDTOToEntity(ReservationDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        ReservationModel entity = new ReservationModel();
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setTrajetModel(trajetService.generateEntityByDTO(dto.getTrajetDTO()));
        entity.setNbPlaceReserv(dto.getNbPlacesReserv());
        entity.setDateReservation(dto.getDateReservation());
        entity.setStatut(dto.getStatut());
        return entity;
    }
}
