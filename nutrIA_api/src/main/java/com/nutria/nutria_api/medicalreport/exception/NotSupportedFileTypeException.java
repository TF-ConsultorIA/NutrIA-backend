package com.nutria.nutria_api.medicalreport.exception;

import com.nutria.nutria_api.shared.exception.BusinessRuleException;

public class NotSupportedFileTypeException extends BusinessRuleException {
    public NotSupportedFileTypeException() {
        super("Tipo de archivo no soportado, solo se permiten archivos de tipo pdf");
    }
}
