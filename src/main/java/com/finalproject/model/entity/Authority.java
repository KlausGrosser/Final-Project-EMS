package com.finalproject.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authorities for user
 *
 * @see User
 */
public enum Authority implements GrantedAuthority {
    USER,
    ADMIN,
    SUPERADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
