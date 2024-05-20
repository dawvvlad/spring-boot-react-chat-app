package com.vlad.server.config.security;

import com.vlad.server.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetail implements UserDetails {
    private final User user;

    public MyUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Учетная запись никогда не истекает
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Учетная запись никогда не блокируется
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Пароль пользователя никогда не истекает
    }

    @Override
    public boolean isEnabled() {
        return true; // Учетная запись пользователя всегда активна
    }
}
