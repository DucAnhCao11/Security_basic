package com.example.demo.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APPException extends RuntimeException {

    private ErrorCode errorCode;

    public APPException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
