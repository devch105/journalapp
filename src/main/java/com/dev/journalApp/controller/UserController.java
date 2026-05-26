package com.dev.journalApp.controller;

import com.dev.journalApp.entity.User;
import com.dev.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
   private UserService  userService;

    /* Set User */
    @PostMapping("/set-user")
    public ResponseEntity<User> setUser(@RequestBody User User){
        try{
            userService.saveUser(User);
            return new ResponseEntity<>(User , HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*Get All User*/
    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAllusers(){
       try {
           List<User>  list = new ArrayList<>();
           list = userService.getAllusers();
           return new ResponseEntity<>(list , HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

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

    /* Update user by Id*/
    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updateUserById(@PathVariable ObjectId myid , @RequestBody User newuser){
       try {
           User user = userService.findById(myid).orElse(null);
           if(user !=null){
               user.setUserName( newuser.getUserName() !=  null && !newuser.getUserName().equals("") ? newuser.getUserName() : user.getUserName());
               user.setPassword(newuser.getPassword() != null  && !newuser.getPassword().equals("") ? newuser.getPassword() : user.getPassword());
           }

           userService.saveUser(user);
           return new ResponseEntity<>(user , HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }


    @PutMapping("/name/{name}")
    public ResponseEntity<?> updateUserByName(@PathVariable String name, @RequestBody User newuser){
        try {
            User user = userService.findByName(name);
            if(user !=null){
                user.setUserName( newuser.getUserName() !=  null && !newuser.getUserName().equals("") ? newuser.getUserName() : user.getUserName());
                user.setPassword(newuser.getPassword() != null  && !newuser.getPassword().equals("") ? newuser.getPassword() : user.getPassword());
            }

            userService.saveUser(user);
            return new ResponseEntity<>(user , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
