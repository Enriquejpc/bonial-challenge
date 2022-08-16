package com.bonial.challenge.core.exception;

import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.configuration.exception.GenericException;

public class AdapterException extends GenericException {

    public AdapterException(ErrorCode ec){ super(ec);}
}