package com.nutria.nutria_api.profile.exception;

import com.nutria.nutria_api.shared.exception.BusinessRuleException;

import java.time.LocalDate;

public class UserWeightAlreadyRegisteredException extends BusinessRuleException {
    public UserWeightAlreadyRegisteredException(LocalDate date) {
        super("Ya existe un registro de peso para la fecha " + date);
    }
}
