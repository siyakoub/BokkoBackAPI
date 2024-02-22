package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum MessageMessageEnum {

    ERROR_MESSAGE_CREATION("Error during the creation of the message..."),

    ERROR_MESSAGE_UPDATE("Error during the update of the message..."),

    ERROR_MESSAGE_DELETE("Error during the update of the message..."),

    MESSAGE_NOT_FOUND("Message not found...");

    @Getter
    private final String message;

    MessageMessageEnum(final String message) {
        this.message = message;
    }

}
