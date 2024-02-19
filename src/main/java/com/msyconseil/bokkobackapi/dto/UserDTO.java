package com.msyconseil.bokkobackapi.dto;

import java.io.Serial;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String firstName;

    private String email;

    private String password;

    private String phoneNumber;

    private String statut;

    public UserDTO() {
        super();
    }

}
