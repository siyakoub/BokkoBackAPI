package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.TrajetMessageEnum;

import java.io.Serial;

public class TrajetException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public TrajetException(TrajetMessageEnum trajetMessageEnum, String message) {
        super(trajetMessageEnum.name() + ":" + trajetMessageEnum.getMessage() + "--" + message);
    }

    public TrajetException(TrajetMessageEnum trajetMessageEnum) {
        super(trajetMessageEnum.name() + ":" + trajetMessageEnum.getMessage());
    }

}
