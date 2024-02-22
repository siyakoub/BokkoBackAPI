package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.ProfilMessageEnum;

import java.io.Serial;

public class ProfilException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProfilException(ProfilMessageEnum profilMessageEnum, String message) {
        super(profilMessageEnum.name() + ":" + profilMessageEnum.getMessage() + "--" + message);
    }

    public ProfilException(ProfilMessageEnum profilMessageEnum) {
        super(profilMessageEnum.name() + ":" + profilMessageEnum.getMessage());
    }

}
