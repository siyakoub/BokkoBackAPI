package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.VehiculeMessageEnum;

import java.io.Serial;

public class VehiculeException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public VehiculeException(VehiculeMessageEnum vehiculeMessageEnum, String message) {
        super(vehiculeMessageEnum.name() + ":" + vehiculeMessageEnum.getMessage() + "--" + message);
    }

    public VehiculeException(VehiculeMessageEnum vehiculeMessageEnum) {
        super(vehiculeMessageEnum.name() + ":" + vehiculeMessageEnum.getMessage());
    }

}
