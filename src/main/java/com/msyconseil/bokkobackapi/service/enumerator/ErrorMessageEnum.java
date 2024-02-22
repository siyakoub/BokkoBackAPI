package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;
import lombok.Setter;

public enum ErrorMessageEnum {

    INVALID_PARAMETER("Invalid parameter !"),
    TOKEN_EXPIRED("Token expired !"),
    ENTITY_FABRICATION_ERROR("Error during the fabrication of the entity."),
    DTO_FABRICATION_ERROR("Error during the fabrication of the DTO..."),
    ENTITY_CREATION_ERROR("Error during the creation of the entity."),
    ENTITY_UPDATE_ERROR("Error during the update of the entity."),
    ENTITY_MANUFACTURING_ERROR("Error during the manufacturing of the entity."),
    ENTITY_NOT_EXISTS("Entity not existing in the system."),
    UNKNOWN_ERROR("Unknown error."),
    SQL_ERROR("Database Error."),
    ACTION_UNAUTHORISED_ERROR("Action unauthorised"),
    USER_CREATION_ERROR("Error during the creation of the user."),
    MONITORING_APPOINTMENT_ERROR("Error during the monitoring appointment"),
    INVALID_TOKEN("Invalid token !");

    @Getter
    private final String message;

    ErrorMessageEnum(final String message){
        this.message = message;
    }

}
