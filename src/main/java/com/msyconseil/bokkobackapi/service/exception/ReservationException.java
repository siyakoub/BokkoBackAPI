package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.ReservationMessageEnum;

import java.io.Serial;

public class ReservationException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ReservationException(ReservationMessageEnum reservationMessageEnum, String message) {
        super(reservationMessageEnum.name() + ":" + reservationMessageEnum.getMessage() + "--" + message);
    }

    public ReservationException(ReservationMessageEnum reservationMessageEnum) {
        super(reservationMessageEnum.name() + ":" + reservationMessageEnum.getMessage());
    }

}
