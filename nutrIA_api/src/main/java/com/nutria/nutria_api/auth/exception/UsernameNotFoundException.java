package com.nutria.nutria_api.auth.exception;

import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;

public class UsernameNotFoundException extends ResourceNotFoundException {
    public UsernameNotFoundException(String email) {
        super("El usuario " + email + " no existe");
    }
}
