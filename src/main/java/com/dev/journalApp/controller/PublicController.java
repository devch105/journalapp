package com.dev.journalApp.controller;


import com.dev.journalApp.entity.User;
import com.dev.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private UserService userService;

    /* Set User */
    @PostMapping("/set-user")
    public ResponseEntity<User> setUser(@RequestBody User User){
        try{
            userService.saveNewUser(User);
            return new ResponseEntity<>(User , HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            List<User> list = userService.getAllusers();
            System.out.println("Users found: " + list.size()); // 👈 add this
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace(); // 👈 add this
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Health is OK ";
    }
}
