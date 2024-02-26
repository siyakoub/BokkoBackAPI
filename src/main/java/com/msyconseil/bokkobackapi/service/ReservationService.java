package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.ReservationDTO;
import com.msyconseil.bokkobackapi.model.ReservationModel;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.ReservationException;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import com.msyconseil.bokkobackapi.service.interf.IService;

import java.util.List;
import java.util.Map;


public class ReservationService extends AbstractService<ReservationDTO, ReservationModel> implements IService<ReservationModel, ReservationDTO>, ICRUDService<ReservationDTO, String> {
    public ReservationService(SessionService sessionService) {
        super(sessionService);
    }

    @Override
    public CustomAnswer<ReservationDTO> get(Map<String, String> headers, String email) throws Exception {
        return null;
    }

    @Override
    public CustomListAnswer<List<ReservationDTO>> getAll(Map<String, String> headers, int page, int size) throws Exception {
        return null;
    }

    @Override
    public CustomAnswer<ReservationDTO> add(Map<String, String> headers, ReservationDTO parameter) throws Exception {
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
        return null;
    }

    @Override
    public ReservationDTO generateDTOByEntity(ReservationModel entity) throws ErrorException {
        return null;
    }
}
