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

import java.time.LocalDateTime;
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

    @Transactional
    public MessageDTO add(MessageDTO messageDTO) throws ErrorException {
        if (messageDTO == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        MessageModel messageModel = generateEntityByDTO(messageDTO);
        messageModel = messageRepository.save(messageModel);
        return generateDTOByEntity(messageModel);
    }

    public MessageDTO add(MessageModel messageModel) throws ErrorException {
        return generateDTOByEntity(messageRepository.save(messageModel));
    }

    private List<MessageModel> getAllMessage() {
        return messageRepository.findAllMessage();
    }

    private List<MessageModel> getAllByExpediteur(int idExpediteur) {
        return messageRepository.findAllMessageByExpediteur(idExpediteur);
    }

    private List<MessageModel> getAllByDestinataire(int idDestinataire) {
        return messageRepository.findAllMessageByDestinateur(idDestinataire);
    }

    private List<MessageModel> getAllConvByDestinataireAndExpediteur(int idExpediteur, int idDestinataire) {
        return messageRepository.findAllMessageByExpediteurForDestinataire(idExpediteur, idDestinataire);
    }

    private MessageModel getLastMessageInConversation(int idExpediteur, int idDestinataire) {
        return messageRepository.findLastMessageInConversation(idExpediteur, idDestinataire);
    }

    private MessageModel getMessageById(int id) {
        return messageRepository.findMessageById(id);
    }

    private MessageModel getLastMessageForUser(int idUser) {
        return messageRepository.findLastMessageForUser(idUser);
    }

    @Override
    public CustomAnswer<MessageDTO> get(Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        CustomAnswer<MessageDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            MessageModel messageModel = getLastMessageForUser(sessionModel.getUserModel().getId());
            if (messageModel != null) {
                MessageDTO messageDTO = generateDTOByEntity(messageModel);
                response.setContent(messageDTO);
            } else {
                throw new MessageException(MessageMessageEnum.MESSAGE_NOT_FOUND);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomListAnswer<List<MessageDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<MessageDTO>> response = new CustomListAnswer<>();
        try {
            getActiveSession(headers);
            List<MessageDTO> list = new LinkedList<>();
            for (MessageModel messageModel: getAllMessage()) {
                list.add(generateDTOByEntity(messageModel));
            }
            if (list.isEmpty()) {
                throw new MessageException(MessageMessageEnum.MESSAGE_NOT_FOUND);
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
    public CustomAnswer<MessageDTO> add(Map<String, String> headers, MessageDTO parameter) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<MessageDTO> response = new CustomAnswer<>();
        try {
            getActiveSession(headers);
            MessageDTO dto = add(parameter);
            response.setContent(dto);
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<MessageDTO> update(Map<String, String> headers, MessageDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<MessageDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            MessageModel messageModel = getLastMessageForUser(sessionModel.getUserModel().getId());
            if (messageModel != null){
                updateInformation(messageModel, parameter);
                messageModel = messageRepository.save(messageModel);
                MessageDTO messageDTO = generateDTOByEntity(messageModel);
                response.setContent(messageDTO);
            }else {
                throw new MessageException(MessageMessageEnum.ERROR_MESSAGE_UPDATE);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<MessageDTO> delete(Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<MessageDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            MessageModel messageModel = getLastMessageForUser(sessionModel.getUserModel().getId());
            if (messageModel != null) {
                messageRepository.deleteById(messageModel.getId());
                MessageDTO messageDTO = generateDTOByEntity(messageModel);
                response.setContent(messageDTO);
            } else {
                throw new MessageException(MessageMessageEnum.ERROR_MESSAGE_DELETE);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    private void updateInformation(MessageModel messageModel, MessageDTO messageDTO) {
        messageModel.setContenu(messageDTO.getContenu());
        messageModel.setLu(false);
        messageModel.setDateHeureEnvoi(LocalDateTime.now());
    }

    @Override
    public MessageModel generateEntityByDTO(MessageDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public MessageModel mapEntityByWithDTO(MessageModel entity, MessageDTO dto) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        entity.setExpediteur(userService.generateEntityByDTO(dto.getExpediteur()));
        entity.setDestinataire(userService.generateEntityByDTO(dto.getDestinataire()));
        entity.setContenu(dto.getContenu());
        entity.setDateHeureEnvoi(dto.getDateHeureEnvoi());
        entity.setLu(dto.isLu());
        return entity;
    }

    @Override
    public MessageDTO generateDTOByEntity(MessageModel entity) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setExpediteur(userService.generateDTOByEntity(entity.getExpediteur()));
        messageDTO.setDestinataire(userService.generateDTOByEntity(entity.getDestinataire()));
        messageDTO.setContenu(entity.getContenu());
        messageDTO.setDateHeureEnvoi(entity.getDateHeureEnvoi());
        messageDTO.setLu(entity.isLu());
        return messageDTO;
    }

    @Override
    public MessageModel mapDTOToEntity(MessageDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        MessageModel messageModel = new MessageModel();
        messageModel.setExpediteur(userService.generateEntityByDTO(dto.getExpediteur()));
        messageModel.setDestinataire(userService.generateEntityByDTO(dto.getDestinataire()));
        messageModel.setContenu(dto.getContenu());
        messageModel.setDateHeureEnvoi(dto.getDateHeureEnvoi());
        messageModel.setLu(dto.isLu());
        return messageModel;
    }
}
