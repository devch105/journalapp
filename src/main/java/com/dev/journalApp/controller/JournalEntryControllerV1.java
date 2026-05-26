package com.dev.journalApp.controller;

import com.dev.journalApp.Repository.JournalEntryRepository;
import com.dev.journalApp.entity.JournalEntry;
import com.dev.journalApp.entity.User;
import com.dev.journalApp.service.JournalEntryService;
import com.dev.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.FloatControl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV1 {

   @Autowired
   private JournalEntryService journalEntryService;
   @Autowired
   private UserService userService;

    @PostMapping("/set-entry/{username}")
    public ResponseEntity<JournalEntry> setEntries(@PathVariable String username ,  @RequestBody JournalEntry journalEntry){
     try {
         User user = userService.findByName(username);

         if(user!=null){
             journalEntryService.saveEntry(journalEntry,user);
         return  new ResponseEntity<> (journalEntry, HttpStatus.CREATED);
         }
         return new ResponseEntity<> (journalEntry, HttpStatus.BAD_REQUEST);
     }catch (Exception e){
         return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
     }
    }

    // get all entry of user by name
    @GetMapping("/get-all/{username}")
    public ResponseEntity<List<JournalEntry>> getAllEntriesofUser(@PathVariable String username){
           User user = userService.findByName(username);
           List<JournalEntry>  list = user.getJournalEntries();
           if(!list.isEmpty()){
               return new ResponseEntity<>(list , HttpStatus.OK);
           }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/id/{myid}")
    public  ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid){

      Optional<JournalEntry> entry =   journalEntryService.findById(myid);

      if(entry.isPresent()){
          return new ResponseEntity<>(entry.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable  ObjectId myid , @PathVariable String username){
        try{
            if(journalEntryService.findById(myid).isPresent()){
                journalEntryService.deleteById(myid,username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/id/{username}/{myid}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myid , @RequestBody JournalEntry newEntry, @PathVariable String username){
       try {
           JournalEntry entry = journalEntryService.findById(myid).orElse(null);
           if(entry !=null){
               entry.setTitle( newEntry.getTitle() !=  null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : entry.getTitle());
               entry.setContent(newEntry.getContent() != null  && !newEntry.getContent().equals("") ? newEntry.getContent() : entry.getContent());
           }
           User user = userService.findByName(username);
           journalEntryService.saveEntry(entry, user);
           return new ResponseEntity<>(entry , HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

}
