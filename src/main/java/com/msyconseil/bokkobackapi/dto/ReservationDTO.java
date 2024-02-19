package com.msyconseil.bokkobackapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@ToString
public class ReservationDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private TrajetDTO trajetDTO;

    private UserDTO userDTO;

    private int nbPlacesReserv;

    private String statut;

    public ReservationDTO() {
        super();
    }



}
