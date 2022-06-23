package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDao userDao;
    @Autowired
    UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUser(username);
        if (user != null) {
            return user;
        } else {
            throw new BadCredentialsException("Wrong username");
        }
    }
}
