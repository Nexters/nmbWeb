package com.teamnexters.nmbweb.security.service;

import com.teamnexters.nmbweb.entity.UserEntity;
import com.teamnexters.nmbweb.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by limjuhyun on 8/2/16.
 */
@Component
public class UserLogin implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUserid(username);
        User user = new User(userEntity.getUserid(), userEntity.getPasswd(),getAuthorities(2));
        return user;
    }

    /**
     * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
     * @param role the numerical role
     * @return a collection of {@link GrantedAuthority
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    /**
     * Converts a numerical role to an equivalent list of roles
     * @param role the numerical role
     * @return list of roles as as a list of {@link String}
     */
    private List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
            roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_USER");
        }

        return roles;
    }

    /**
     * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
     * @param roles {@link String} of roles
     * @return list of granted authorities
     */
    private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
