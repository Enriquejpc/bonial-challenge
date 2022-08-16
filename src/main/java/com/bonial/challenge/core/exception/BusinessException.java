package com.bonial.challenge.core.exception;

import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.configuration.exception.GenericException;

public class BusinessException extends GenericException {

    public BusinessException(ErrorCode ec){ super(ec);}
}