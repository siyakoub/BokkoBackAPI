package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum UserStatusEnum {

    ACTIF("A"),
    INACTIF("I"),
    BLOCKED("B");

    @Getter
    private final String message;

    UserStatusEnum(final String message){
        this.message = message;
    }
}
