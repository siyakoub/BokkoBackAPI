package com.msyconseil.bokkobackapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AbstractDTO {

    @Schema(description = "Id de l'instance en question. Elle est renseignée automatiquement par le système")
    protected transient Integer Id;

}
