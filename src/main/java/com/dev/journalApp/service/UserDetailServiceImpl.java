package com.dev.journalApp.service;

import com.dev.journalApp.Repository.UserRepository;
import com.dev.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user =  userRepository.findByUserName(username);
      if(user!=null){
       UserDetails userDetails =   org.springframework.security.core.userdetails.User
                  .builder()
                  .username(user.getUserName())
                  .password(user.getPassword())
                  .roles(user.getRoles() != null && !user.getRoles().isEmpty()
                          ? user.getRoles().toArray(new String[0]): new String[] {"USER"})
                  .build();
       return userDetails;
      }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }



}
