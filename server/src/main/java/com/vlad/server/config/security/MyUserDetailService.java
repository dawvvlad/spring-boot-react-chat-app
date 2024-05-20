package com.vlad.server.config.security;

import com.vlad.server.entity.User;
import com.vlad.server.repo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    public MyUserDetailService(){}
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepo.findByUsername(username);
            return new MyUserDetail(user);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }
}