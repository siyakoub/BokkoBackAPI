package com.msyconseil.bokkobackapi.service;

import java.time.LocalDateTime;
import java.util.Map;

import com.msyconseil.bokkobackapi.repository.SessionRepository;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;


@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public SessionModel add(UserModel userModel){
        if (userModel.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        SessionModel sessionModel = new SessionModel(userModel);
        sessionModel = sessionRepository.save(sessionModel);
        return sessionModel;
    }

    public SessionModel getSessionByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    public SessionModel getActiveSessionByToken(String token) throws ErrorException {
        if (token == null || token.trim().equalsIgnoreCase("")){
            throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
        }
        SessionModel session = getSessionByToken(token);
        if (session == null) {
            throw new ErrorException(ErrorMessageEnum.TOKEN_EXPIRED);
        }
        if(session.getDateFinSession().isBefore(LocalDateTime.now())) {
            throw new ErrorException(ErrorMessageEnum.TOKEN_EXPIRED);
        }
        return session;
    }

    public boolean destroySession(String token){
        try {
            sessionRepository.delete(token);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
