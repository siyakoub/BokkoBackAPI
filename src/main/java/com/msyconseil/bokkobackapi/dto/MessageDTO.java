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
public class MessageDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDTO expediteur;

    private UserDTO destinataire;

    private String contenu;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateHeureEnvoi;

    private boolean lu;

    public MessageDTO() {
        super();
    }






}
