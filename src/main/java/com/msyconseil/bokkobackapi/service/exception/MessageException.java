package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.MessageMessageEnum;

import java.io.Serial;

public class MessageException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public MessageException(MessageMessageEnum messageMessageEnum, String message) {
        super(messageMessageEnum.name() + ":" + messageMessageEnum.getMessage() + "--" + message);
    }

    public MessageException(MessageMessageEnum messageMessageEnum) {
        super(messageMessageEnum.name() + ":" + messageMessageEnum.getMessage());
    }

}
