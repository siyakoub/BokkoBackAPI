package com.msyconseil.bokkobackapi.service.enumerator;

import lombok.Getter;

public enum ReservationMessageEnum {

    ERROR_RESERVATION_CREATION("Error during the creation of the reservation..."),

    ERROR_RESERVATION_UPDATE("Error during the update of the reservation..."),

    ERROR_RESERVATION_DELETE("Error during the delete of the reservation..."),

    RESERVATION_NOT_FOUND("Reservation not found...");

    @Getter
    private final String message;

    ReservationMessageEnum(final String message) {
        this.message = message;
    }

}
