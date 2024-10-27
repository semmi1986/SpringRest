package com.itMentor.Task312.service.Impl;

import com.itMentor.Task312.model.Role;
import com.itMentor.Task312.model.User;
import com.itMentor.Task312.repositories.UserRepository;
import com.itMentor.Task312.security.UserAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserAuthDetailServiceImpl implements UserDetailsService{

    private final UserRepository repository;

    @Autowired
    public UserAuthDetailServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> user = repository.findByUsername(username);
        System.out.println("loadUserByUsername");
        System.out.println(user.get());
        for (Role r : user.get().getSetRoles()) {
            System.out.println(r.getName());
        }
       if (user.isPresent()) {
            return new UserAuthDetails(user.get());
       }else {
           throw new UsernameNotFoundException(username);
       }
    }

}
