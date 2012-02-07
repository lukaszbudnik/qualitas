package com.google.code.qualitas.services.security.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * The Class QualitasUserDetailsService.
 */
@Service
public class QualitasUserDetailsService implements UserDetailsService {

    /** The Constant QUALITAS_ROLE_USER. */
    private static final String QUALITAS_ROLE_USER = "ROLE_USER";

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl(QUALITAS_ROLE_USER));

        // password cannot be null or empty, otherwise there is IllegalArgumentException thrown
        User user = new User(username, "[PROTECTED]", true, true, true, true, authorities);
        return user;
    }

}
