package com.msyconseil.bokkobackapi.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msyconseil.bokkobackapi.service.enumerator.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Le nom de famille de l'utilisateur est requis...")
    private String name;

    private boolean passwordIsChange = false;

    @NotEmpty(message = "Le prénom de l'utilisateur est requis...")
    private String firstName;

    @NotEmpty(message = "L'email de l'utilisateur est requis !")
    private String email;

    @NotEmpty(message = "Le mot de passe est requis !")
    private String password;

    @NotEmpty(message = "Le numéro de l'utilisateur est requis !")
    private String phoneNumber;

    private String token;

    private LocalDateTime dateInscription;

    @NotEmpty(message = "Le statut de l'utilisateur est requis")
    private UserStatusEnum statut;

    public UserDTO() {
        super();
    }

}
