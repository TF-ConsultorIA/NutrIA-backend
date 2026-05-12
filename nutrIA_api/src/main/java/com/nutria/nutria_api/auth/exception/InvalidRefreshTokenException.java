package com.nutria.nutria_api.auth.exception;

import com.nutria.nutria_api.shared.exception.BusinessRuleException;

public class InvalidRefreshTokenException extends BusinessRuleException {
    public InvalidRefreshTokenException() {
        super("refresh token invalido, expirado o revocado");
    }
}
