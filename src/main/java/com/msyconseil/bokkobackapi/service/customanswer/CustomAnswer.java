package com.msyconseil.bokkobackapi.service.customanswer;

import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Mourad Si Yakoub
 * @param <T>
 */

@Component
@ToString
public class CustomAnswer<T extends Object> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    protected boolean hasError = false;

    @Getter @Setter
    protected T content;

    @Getter
    protected String errorMessage;

    private ErrorException errorException;

    public void setErrorMessage(String errorMessage) {
        hasError = true;
        if (!(errorMessage == null || errorMessage.trim().isEmpty())) {
            this.errorMessage = errorMessage;
        }
    }

    public void setErrorException(ErrorException errorException) {
        this.errorException = errorException;
        this.setErrorMessage(errorException.getMessage());
    }


}
