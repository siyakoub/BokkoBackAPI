package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum ProfilMessageEnum {

    ERROR_PROFIL_CREATION("Error during the creation of the profil..."),

    ERROR_PROFIL_UPDATE("Error during the update of the profil..."),

    ERROR_PROFIL_DELETE("Error during the delete of the profil..."),

    PROFIL_NOT_FOUND("Profil not found...");

    @Getter
    private final String message;

    ProfilMessageEnum(final String message) {
        this.message = message;
    }

}
