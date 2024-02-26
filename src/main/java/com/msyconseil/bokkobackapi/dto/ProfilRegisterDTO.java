package com.msyconseil.bokkobackapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProfilRegisterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String firstName;

    private String email;

    private String password;

    private String telephone;

    private String statut;

    private String bio;

    private String photo;

    public ProfilRegisterDTO() {
        super();
    }

}
