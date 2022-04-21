package com.finalproject.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authorities for user
 *
 * @see User
 */
public enum Authority implements GrantedAuthority {
    //Regular employees
    USER,
    //Only for HR Agents
    HR_AGENT,
    //Only for HR Supervisors
    HR_SUPERVISOR,
    //Department Supervisors
    SUPERVISOR,
    //Owner
    SUPERADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
