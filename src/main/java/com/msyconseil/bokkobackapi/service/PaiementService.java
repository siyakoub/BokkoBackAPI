package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.PaiementDTO;
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
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

@Service
public class PaiementService extends AbstractService<PaiementDTO, PaiementModel> implements IService<PaiementModel, PaiementDTO>, ICRUDService<PaiementDTO, String> {

    @Autowired
    UserService userService;

    @Autowired
    PaiementRepository paiementRepository;

    public PaiementService(SessionService sessionService) {
        super(sessionService);
    }

    @Override
    public CustomAnswer<PaiementDTO> get(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomListAnswer<List<PaiementDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<PaiementDTO> add(Map<String, String> headers, PaiementDTO parameter) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<PaiementDTO> update(Map<String, String> headers, PaiementDTO parameter, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<PaiementDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public PaiementModel generateEntityByDTO(PaiementDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public PaiementModel mapEntityByWithDTO(PaiementModel entity, PaiementDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public PaiementDTO generateDTOByEntity(PaiementModel entity) throws ErrorException {
        return null;
    }

    @Override
    public PaiementModel mapDTOToEntity(PaiementDTO dto) throws ErrorException {
        return null;
    }
}
