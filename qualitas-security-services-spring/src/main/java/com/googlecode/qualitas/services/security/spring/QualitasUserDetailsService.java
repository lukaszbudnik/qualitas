package com.googlecode.qualitas.services.security.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.googlecode.qualitas.internal.dao.Repository;
import com.googlecode.qualitas.internal.model.User_;

/**
 * The Class QualitasUserDetailsService.
 */
@Service
public class QualitasUserDetailsService implements UserDetailsService {

    /** The Constant QUALITAS_ROLE_USER. */
    private static final String QUALITAS_ROLE_USER = "ROLE_USER";

    /** The repository. */
    @Autowired
    private Repository repository;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        com.googlecode.qualitas.internal.model.User user = repository.getSingleResultBySingularAttribute(
                com.googlecode.qualitas.internal.model.User.class, User_.openIDUsername, username);
        
        if (user == null) {
            user = new com.googlecode.qualitas.internal.model.User();
            user.setOpenIDUsername(username);
            repository.persist(user);
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl(QUALITAS_ROLE_USER));

        // password cannot be null or empty, otherwise there is
        // IllegalArgumentException thrown
        UserDetails userDetails = new User(username, "[PROTECTED]", true, true, true, true, authorities);
        return userDetails;
    }

}
