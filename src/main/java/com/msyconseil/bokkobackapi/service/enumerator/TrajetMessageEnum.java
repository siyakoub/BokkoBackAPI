package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum TrajetMessageEnum {

    ERROR_TRAJET_CREATION("Error during the creation of the journey..."),

    ERROR_TRAJET_UPDATE("Error during the update of the journey..."),

    ERROR_TRAJET_DELETE("Error during the delete of the journey..."),

    NOT_FOUND("Journey not found..."),

    PLACES_UNAVAILABLE("Journey unavailable...");

    @Getter
    private final String message;

    TrajetMessageEnum(final String message) {
        this.message = message;
    }



}
