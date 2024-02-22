package com.msyconseil.bokkobackapi.controller;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractController<D> {

    public ResponseEntity<CustomAnswer<D>> generateResponseEntity(CustomAnswer<D> customAnswer){
        if (customAnswer == null || customAnswer.isHasError()){
            return new ResponseEntity<>(customAnswer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customAnswer, HttpStatus.OK);
    }


}
