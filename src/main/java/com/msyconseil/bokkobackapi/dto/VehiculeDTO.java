package com.msyconseil.bokkobackapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.SerializableString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class VehiculeDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDTO userDTO;

    private String marque;

    private String modele;

    private String couleur;

    private String immatriculation;

    private int annee;

    public VehiculeDTO() {
        super();
    }

}
