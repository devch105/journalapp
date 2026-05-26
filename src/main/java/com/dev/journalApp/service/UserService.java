package com.dev.journalApp.service;


import com.dev.journalApp.Repository.UserRepository;
import com.dev.journalApp.entity.JournalEntry;
import com.dev.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // save and Update Entry
    public boolean saveUser(User user){
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
