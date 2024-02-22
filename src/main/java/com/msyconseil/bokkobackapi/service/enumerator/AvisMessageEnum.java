package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum AvisMessageEnum {

    ERROR_CREATION_AVIS("Error during the creation of the review..."),

    ERROR_DELETING_AVIS("Error during the delete of the review..."),

    ERROR_UPDATE_AVIS("Error during the update of the review"),

    REVIEW_NOT_FOUND("review not found...");

    @Getter
    private final String message;

    AvisMessageEnum(final String message) {
        this.message = message;
    }

}
