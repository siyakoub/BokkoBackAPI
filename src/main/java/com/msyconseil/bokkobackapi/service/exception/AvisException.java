package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.AvisMessageEnum;

import java.io.Serial;

public class AvisException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public AvisException(AvisMessageEnum avisMessageEnum, String message) {
        super(avisMessageEnum.name() + ":" + avisMessageEnum.getMessage() + "--" + message);
    }

    public AvisException(AvisMessageEnum avisMessageEnum) {
        super(avisMessageEnum.name() + ":" + avisMessageEnum.getMessage());
    }

}
