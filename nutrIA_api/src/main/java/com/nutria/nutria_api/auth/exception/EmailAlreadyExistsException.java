package com.nutria.nutria_api.auth.exception;

import com.nutria.nutria_api.shared.exception.BusinessRuleException;

public class EmailAlreadyExistsException extends BusinessRuleException {
    public EmailAlreadyExistsException(String email) {
        super("El email " + email + " ya existe");
    }
}
