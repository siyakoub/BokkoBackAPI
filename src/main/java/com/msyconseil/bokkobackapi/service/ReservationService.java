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
import java.util.Objects;

import static org.springframework.data.projection.EntityProjection.ProjectionType.DTO;

@Service
public class ReservationService extends AbstractService<ReservationDTO, ReservationModel> implements IService<ReservationModel, ReservationDTO>, ICRUDService<ReservationDTO, String> {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    TrajetService trajetService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrajetRepository trajetRepository;


    public ReservationService(SessionService sessionService) {
        super(sessionService);
    }

    @Transactional
    public ReservationDTO add(ReservationDTO reservationDTO) throws ErrorException, TrajetException {
        if (reservationDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        ReservationModel reservationModel = generateEntityByDTO(reservationDTO);
        if (reservationDTO.getUserDTO().getId() != null) {
            UserModel user = userRepository.findById(reservationDTO.getUserDTO().getId())
                    .orElseThrow(() -> new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS));
            reservationModel.setUserModel(user);
            if (reservationDTO.getTrajetDTO().getId() != null) {
                TrajetModel trajetModel = trajetRepository.findById(reservationDTO.getTrajetDTO().getId())
                        .orElseThrow(() -> new TrajetException(TrajetMessageEnum.NOT_FOUND));
                reservationModel.setTrajetModel(trajetModel);
            }
        }
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

    private List<ReservationModel> getAllReservationByTrajetInProgress(int idTrajet) {
        return reservationRepository.findAllReservationByTrajetInProgress(idTrajet);
    }

    private List<ReservationModel> getAllReservationByTrajetConfirmed(int idTrajet) {
        return reservationRepository.findAllReservationByTrajetConfirmed(idTrajet);
    }

    private List<ReservationModel> getAllReservationByTrajetFinished(int idTrajet) {
        return reservationRepository.findAllReservationByTrajetFinished(idTrajet);
    }

    private List<ReservationModel> getAllReservationByTrajetCanceled(int idTrajet) {
        return reservationRepository.findAllReservationByTrajetCanceled(idTrajet);
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

    public CustomListAnswer<List<ReservationDTO>> getAllByTrajetInProgress(final Map<String, String> headers, String email, int idTrajet) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<ReservationDTO>> response = new CustomListAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(sessionModel.getUserModel().getEmail(), email)) {
                List<ReservationDTO> list = new LinkedList<>();
                for (ReservationModel reservationModel : getAllReservationByTrajetInProgress(idTrajet)) {
                    list.add(generateDTOByEntity(reservationModel));
                }
                if (list.isEmpty()) {
                    throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
                } else {
                    response.setContent(list);
                }
            } else {
                throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public CustomListAnswer<List<ReservationDTO>> getAllByTrajet(final Map<String, String> headers, String email, int idTrajet) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<ReservationDTO>> response = new CustomListAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(sessionModel.getUserModel().getEmail(), email)) {
                List<ReservationDTO> list = new LinkedList<>();
                for (ReservationModel reservationModel : getAllReservationByTrajet(idTrajet)) {
                    list.add(generateDTOByEntity(reservationModel));
                }
                if (list.isEmpty()) {
                    throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
                } else {
                    response.setContent(list);
                }
            } else {
                throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public CustomListAnswer<List<ReservationDTO>> getAllByUser(final Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<ReservationDTO>> response = new CustomListAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(email, sessionModel.getUserModel().getEmail())) {
                List<ReservationDTO> list = new LinkedList<>();
                for (ReservationModel reservationModel : getAllReservationByPassager(sessionModel.getUserModel().getId())) {
                    list.add(generateDTOByEntity(reservationModel));
                }
                if (list.isEmpty()) {
                    throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
                } else {
                    response.setContent(list);
                }
            } else {
                throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
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
            for (ReservationModel reservationModel : getAllReservation()){
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
            if (Objects.equals(sessionModel.getUserModel().getEmail(), email)) {
                ReservationModel reservationModel = getById(parameter.getId());
                if (reservationModel != null) {
                    updateInformation(reservationModel, parameter);
                    reservationModel = reservationRepository.save(reservationModel);
                    ReservationDTO reservationDTO = generateDTOByEntity(reservationModel);
                    response.setContent(reservationDTO);
                } else {
                    throw new ReservationException(ReservationMessageEnum.ERROR_RESERVATION_UPDATE);
                }
            } else {
                throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public CustomAnswer<ReservationDTO> deleteById(final Map<String, String> headers, String email, int idReservation) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ReservationDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(sessionModel.getUserModel().getEmail(), email)) {
                ReservationModel reservationModel = getById(idReservation);
                if (reservationModel != null) {
                    reservationRepository.deleteById(reservationModel.getId());
                    ReservationDTO reservationDTO = generateDTOByEntity(reservationModel);
                    response.setContent(reservationDTO);
                } else {
                    throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
                }
            } else {
                throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
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
        entity.setId(dto.getId());
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
        reservationDTO.setId(entity.getId());
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
        entity.setId(dto.getId());
        return entity;
    }
}
