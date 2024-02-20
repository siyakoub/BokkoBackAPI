package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;

import java.io.Serial;

public class ErrorException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorException(ErrorMessageEnum errorMessageEnum, String message){
        super(errorMessageEnum.name() + ": " + errorMessageEnum.getMessage() + " -- " + message);
    }

    public ErrorException(ErrorMessageEnum errorMessageEnum){
        super(errorMessageEnum.name() + ": " + errorMessageEnum.getMessage());
    }

}
