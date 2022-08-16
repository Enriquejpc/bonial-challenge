package com.bonial.challenge.adapter.controller.exception;

import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.exception.BusinessException;

public class ParametersException extends BusinessException {
    public ParametersException(ErrorCode message) {
        super(message);
    }
}
