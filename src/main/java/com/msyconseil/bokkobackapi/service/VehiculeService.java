package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.TrajetDTO;
import com.msyconseil.bokkobackapi.dto.VehiculeDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.model.VehiculeModel;
import com.msyconseil.bokkobackapi.repository.VehiculeRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.VehiculeMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.VehiculeException;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

@Service
public class VehiculeService extends AbstractService<VehiculeDTO, VehiculeModel> implements IService<VehiculeModel, VehiculeDTO>, ICRUDService<VehiculeDTO, String> {

    @Autowired
    VehiculeRepository vehiculeRepository;

    @Autowired
    UserService userService;

    public VehiculeService(SessionService sessionService) {
        super(sessionService);
    }


    @Override
    public CustomAnswer<VehiculeDTO> get(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomListAnswer<List<VehiculeDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<VehiculeDTO> add(Map<String, String> headers, VehiculeDTO parameter) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<VehiculeDTO> update(Map<String, String> headers, VehiculeDTO parameter, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<VehiculeDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public VehiculeModel generateEntityByDTO(VehiculeDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public VehiculeModel mapEntityByWithDTO(VehiculeModel entity, VehiculeDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public VehiculeDTO generateDTOByEntity(VehiculeModel entity) throws ErrorException {
        return null;
    }

    @Override
    public VehiculeModel mapDTOToEntity(VehiculeDTO dto) throws ErrorException {
        return null;
    }
}
