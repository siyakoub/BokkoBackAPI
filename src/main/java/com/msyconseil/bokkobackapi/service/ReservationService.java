package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.ReservationDTO;
import com.msyconseil.bokkobackapi.model.ReservationModel;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.repository.ReservationRepository;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.ReservationMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.ReservationException;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import com.msyconseil.bokkobackapi.service.interf.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService extends AbstractService<ReservationDTO, ReservationModel> implements IService<ReservationModel, ReservationDTO>, ICRUDService<ReservationDTO, String> {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public ReservationService(SessionService sessionService) {
        super(sessionService);
    }

    public ReservationModel getReservationByPassagerUserAndTrajet(int idTrajet, int idPassager) {
        return reservationRepository.findReservationByPassagerAndTrajet(idPassager, idTrajet);
    }

    public List<ReservationModel> getAllReservationByPassager(int idPassager) {
        return reservationRepository.findAllReservationByPassager(idPassager);
    }

    public List<ReservationModel> getAllReservationByTrajet(int idTrajet) {
        return reservationRepository.findByAllByTrajet(idTrajet);
    }

    public List<ReservationModel> getAllReservation() {
        return reservationRepository.findAllReservation();
    }

    public List<ReservationModel> getAllReservationInProgress() {
        return reservationRepository.findReservationByStatutInProgress();
    }

    public List<ReservationModel> getAllReservationConfirmed() {
        return reservationRepository.findReservationByStatutInConfirmation();
    }

    public List<ReservationModel> getAllReservationFinished() {
        return reservationRepository.findReservationByStatutFinished();
    }

    public ReservationModel getById(int id) {
        return reservationRepository.findById(id);
    }

    public ReservationModel getLastReservationInProgressByPassager(int idPassager) {
        return reservationRepository.findLastReservationInProgressByPassager(idPassager);
    }

    public ReservationModel getLastReservationConfirmedByPassager(int idPassager) {
        return reservationRepository.findLastReservationConfirmedByPassager(idPassager);
    }

    public ReservationModel getLastReservationFinishedByPassager(int idPassager) {
        return reservationRepository.findLastReservationFinishedByPassager(idPassager);
    }

    public ReservationModel getLastReservationInProgressByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationInProgressByTrajet(idTrajet);
    }

    public ReservationModel getLastReservationConfirmedByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationConfirmedByTrajet(idTrajet);
    }

    public ReservationModel getLastReservationFinishedByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationFinishedByTrajet(idTrajet);
    }

    public ReservationModel getLastReservationByPassager(int idPassager) {
        return reservationRepository.findLastReservationByPassager(idPassager);
    }

    public ReservationModel getLastReservationByTrajet(int idTrajet) {
        return reservationRepository.findLastReservationByTrajet(idTrajet);
    }

    @Override
    public CustomAnswer<ReservationDTO> get(Map<String, String> headers, String email) throws ErrorException, ReservationException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<ReservationDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            UserModel userModel = userRepository.findByEmail(email);
            if (userModel != null) {
                ReservationModel reservationModel = getLastReservationByPassager(userModel.getId());
                if (reservationModel != null) {

                } else {
                    throw new ReservationException(ReservationMessageEnum.RESERVATION_NOT_FOUND);
                }
            } else {
                throw new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return null;
    }

    @Override
    public CustomListAnswer<List<ReservationDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException, ReservationException {
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
    public CustomAnswer<ReservationDTO> add(Map<String, String> headers, ReservationDTO parameter) throws Exception {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        return null;
    }

    @Override
    public CustomAnswer<ReservationDTO> update(Map<String, String> headers, ReservationDTO parameter, String email) throws Exception {
        return null;
    }

    @Override
    public CustomAnswer<ReservationDTO> delete(Map<String, String> headers, String email) throws Exception {
        return null;
    }

    @Override
    public ReservationModel generateEntityByDTO(ReservationDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public ReservationModel mapEntityByWithDTO(ReservationModel entity, ReservationDTO dto) throws ErrorException {
        if (entity == null) {
            throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        }
        return null;
    }

    @Override
    public ReservationDTO generateDTOByEntity(ReservationModel entity) throws ErrorException {
        return null;
    }

    @Override
    public ReservationModel mapDTOToEntity(ReservationDTO dto) throws ErrorException {
        return null;
    }
}
