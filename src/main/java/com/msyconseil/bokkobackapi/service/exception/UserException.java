package com.msyconseil.bokkobackapi.service.exception;

import com.msyconseil.bokkobackapi.service.enumerator.UserStatusEnum;

import java.io.Serial;

public class UserException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserException(UserStatusEnum userStatusEnum, String message) {
        super(userStatusEnum.name() + ":" + userStatusEnum.getMessage() + "--" + message);
    }

    public UserException(UserStatusEnum userStatusEnum) {
        super(userStatusEnum.name() + ":" + userStatusEnum.getMessage());
    }

}
