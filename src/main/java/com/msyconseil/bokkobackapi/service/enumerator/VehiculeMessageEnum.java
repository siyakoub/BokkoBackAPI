package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum VehiculeMessageEnum {

    ERROR_CREATION_AUTO("Error during the creation of automobile.."),

    NOT_FOUND("Véhicule non trouvé..."),

    ACTION_UNAUTHORISED_AUTO_ERROR("Action unauthorised about this auto."),

    INVALID("Invalid parameter !");

    @Getter
    private final String message;

    VehiculeMessageEnum(final String message) {
        this.message = message;
    }
}
