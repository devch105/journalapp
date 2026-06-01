package com.dev.journalApp.service;


import com.dev.journalApp.Repository.UserRepository;
import com.dev.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private PasswordEncoder passwordEncoder;


    // save and Update Entry
    public boolean updateUser(User user){
        userRepository.save(user);
        return true;
    }

    // save new user
    public boolean saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles()==null && user.getRoles().isEmpty()){
            user.setRoles(List.of("USER"));
        }
        userRepository.save(user);
        return true;
    }





    // get all entry
    public List<User> getAllusers(){
       return userRepository.findAll();
    }

    // get by id
    public Optional<User> findById(ObjectId id){
       return userRepository.findById(id);
    }
    // get by name
    public User findByName(String name){
       return userRepository.findByUserName(name);
    }

    //delete by id
    public boolean deleteById(ObjectId id){
        userRepository.deleteById(id);
        return true;
    }  



}
