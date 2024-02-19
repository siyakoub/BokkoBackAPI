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
public class SessionDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDTO userDTO;

    private String token;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDebutSession;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFinSession;

    private String actif;

    private SessionDTO() {
        super();
    }

}
