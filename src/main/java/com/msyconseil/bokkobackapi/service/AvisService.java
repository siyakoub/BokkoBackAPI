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

@Service
public class AvisService extends AbstractService<AvisDTO, AvisModel> implements IService<AvisModel, AvisDTO>, ICRUDService<AvisDTO, String>{

    @Autowired
    AvisRepository avisRepository;

    @Autowired
    UserService userService;

    public AvisService(SessionService sessionService) {
        super(sessionService);
    }

    @Override
    public CustomAnswer<AvisDTO> get(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomListAnswer<List<AvisDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<AvisDTO> add(Map<String, String> headers, AvisDTO parameter) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<AvisDTO> update(Map<String, String> headers, AvisDTO parameter, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<AvisDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public AvisModel generateEntityByDTO(AvisDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public AvisModel mapEntityByWithDTO(AvisModel entity, AvisDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public AvisDTO generateDTOByEntity(AvisModel entity) throws ErrorException {
        return null;
    }

    @Override
    public AvisModel mapDTOToEntity(AvisDTO dto) throws ErrorException {
        return null;
    }
}
