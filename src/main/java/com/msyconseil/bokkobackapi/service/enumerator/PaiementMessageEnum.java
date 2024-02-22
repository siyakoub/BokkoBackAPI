package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum PaiementMessageEnum {

    ERROR_PAYMENT_CREATION("Error during the creation of the payment..."),

    ERROR_PAYMENT_UPDATE("Error during the update of the payment..."),

    ERROR_PAYMENT_DELETE("Error during the delete of the payment..."),

    PAYMENT_NOT_FOUND("Payment not found...");

    @Getter
    private final String message;

    PaiementMessageEnum(final String message) {
        this.message = message;
    }

}
