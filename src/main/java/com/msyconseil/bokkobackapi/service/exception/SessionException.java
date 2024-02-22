package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.SessionMessageEnum;

import java.io.Serial;

public class SessionException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public SessionException(SessionMessageEnum sessionMessageEnum, String message) {
        super(sessionMessageEnum.name() + ":" + sessionMessageEnum.getMessage() + "--" + message);
    }

    public SessionException(SessionMessageEnum sessionMessageEnum) {
        super(sessionMessageEnum.name() + ":" + sessionMessageEnum.getMessage());
    }

}
