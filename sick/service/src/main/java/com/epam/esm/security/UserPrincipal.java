package com.epam.esm.security;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
