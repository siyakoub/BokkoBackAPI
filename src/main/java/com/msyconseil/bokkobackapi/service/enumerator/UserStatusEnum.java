package com.msyconseil.bokkobackapi.service.enumerator;

public enum UserStatusEnum {

    ACTIF("A"),
    INACTIF("I"),
    BLOCKED("B");

    private final String message;

    UserStatusEnum(final String message){
        this.message = message;
    }
}
