package com.bonial.challenge.adapter.controller.exception;


import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.exception.BusinessException;

public class NotFoundException extends BusinessException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}