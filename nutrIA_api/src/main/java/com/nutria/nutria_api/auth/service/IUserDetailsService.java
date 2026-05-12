package com.nutria.nutria_api.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDetailsService {
    UserDetails loadUserByUsername(String email);
}