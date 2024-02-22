package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.PaiementMessageEnum;

import java.io.Serial;

public class PaiementException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public PaiementException(PaiementMessageEnum paiementMessageEnum, String message) {
        super(paiementMessageEnum.name() + ":" + paiementMessageEnum.getMessage() + "--" + message);
    }

    public PaiementException(PaiementMessageEnum paiementMessageEnum) {
        super(paiementMessageEnum.name() + ":" + paiementMessageEnum.getMessage());
    }

}
