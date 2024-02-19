package com.msyconseil.bokkobackapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class ProfilDTO extends AbstractDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDTO userDTO;

    private String bio;

    private String pictureProfil;

    public ProfilDTO() {
        super();
    }

}
