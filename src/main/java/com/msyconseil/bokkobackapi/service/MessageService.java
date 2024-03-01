package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.MessageDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.MessageModel;
import com.msyconseil.bokkobackapi.repository.MessageRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.MessageMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.MessageException;
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
public class MessageService extends AbstractService<MessageDTO, MessageModel> implements IService<MessageModel, MessageDTO>, ICRUDService<MessageDTO, String> {

    @Autowired
    UserService userService;

    @Autowired
    MessageRepository messageRepository;

    public MessageService(SessionService sessionService) {
        super(sessionService);
    }

    @Override
    public CustomAnswer<MessageDTO> get(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomListAnswer<List<MessageDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<MessageDTO> add(Map<String, String> headers, MessageDTO parameter) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<MessageDTO> update(Map<String, String> headers, MessageDTO parameter, String email) throws ErrorException {
        return null;
    }

    @Override
    public CustomAnswer<MessageDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        return null;
    }

    @Override
    public MessageModel generateEntityByDTO(MessageDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public MessageModel mapEntityByWithDTO(MessageModel entity, MessageDTO dto) throws ErrorException {
        return null;
    }

    @Override
    public MessageDTO generateDTOByEntity(MessageModel entity) throws ErrorException {
        return null;
    }

    @Override
    public MessageModel mapDTOToEntity(MessageDTO dto) throws ErrorException {
        return null;
    }
}
