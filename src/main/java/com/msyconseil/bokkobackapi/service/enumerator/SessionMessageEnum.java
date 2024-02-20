package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum SessionMessageEnum {

    IN_PROGRESS("E"),
    TERMINATED("T");

    @Getter
    private final String message;

    SessionMessageEnum(final String message){
        this.message = message;
    }
}
