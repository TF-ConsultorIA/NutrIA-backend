package com.nutria.nutria_api.auth.exception;

import com.nutria.nutria_api.shared.exception.BusinessRuleException;

public class NotStudentEmailException extends BusinessRuleException {
    public NotStudentEmailException() {
        super("El correo registrado no es de un estudiante");
    }
}
