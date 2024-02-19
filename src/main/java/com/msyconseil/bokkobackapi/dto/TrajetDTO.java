package com.msyconseil.bokkobackapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.security.PublicKey;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TrajetDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDTO userDTO;

    private String depart;

    private String arrivee;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateHeureDepart;

    private int nbPlaces;

    private float prix;

    private String statut;

    public TrajetDTO() {
        super();
    }

}
