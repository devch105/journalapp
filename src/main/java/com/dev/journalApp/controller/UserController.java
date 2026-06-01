package com.dev.journalApp.controller;

import com.dev.journalApp.entity.User;
import com.dev.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
   private UserService  userService;

   @Autowired
   private PasswordEncoder passwordEncoder;
    /*Get User By Id*/
    @GetMapping("/id/{myid}")
    public  ResponseEntity<User> getUserById(@PathVariable ObjectId myid){

      Optional<User> user =   userService.findById(myid);

      if(user.isPresent()){
          return new ResponseEntity<>(user.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*Get User BY name*/
    @GetMapping("/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name){
        User user = userService.findByName(name);
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    /*Delete User bY Id*/
    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteUserById(@PathVariable  ObjectId myid){
        try{
            if(userService.findById(myid).isPresent()){
                userService.deleteById(myid);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserByName( @RequestBody User newuser){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            System.out.println(name);
            User user = userService.findByName(name);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(newuser.getUserName() != null && !newuser.getUserName().trim().isEmpty()){
                user.setUserName(newuser.getUserName());
            }
            if(newuser.getPassword()!=null && !newuser.getPassword().trim().isEmpty()){
                user.setPassword(passwordEncoder.encode(newuser.getPassword()));
            }
            userService.updateUser(user);
            return new ResponseEntity<>(user , HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception -------------------------> "+e.getMessage()+" -------|| ");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
