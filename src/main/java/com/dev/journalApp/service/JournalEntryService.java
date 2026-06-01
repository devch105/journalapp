package com.dev.journalApp.service;

import com.dev.journalApp.Repository.JournalEntryRepository;
import com.dev.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;


import com.dev.journalApp.entity.JournalEntry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    // save and Update Entry
   @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, User user) {
       try {
           journalEntry.setDate(LocalDateTime.now());
           journalEntry.setJournalUserId(user.getId());
           JournalEntry entry =  journalEntryRepository.save(journalEntry);
           // may be some issue arrive  then journal entry will be update but  suer might not update;
           user.getJournalEntries().add(entry);
           userService.updateUser(user);
           return journalEntry;
       }catch (Exception e) {
           System.out.println(e.getMessage());
           throw new RuntimeException("Error saving entry: " + e.getMessage());
       }
    }

    // get all entry
    public List<JournalEntry> getAllEntries(){
       return journalEntryRepository.findAll();
    }

    // get by Id
    public Optional<JournalEntry> findById(ObjectId id){
       return journalEntryRepository.findById(id);
    }

    //delete by id
    public void deleteById(ObjectId id, String username) {
       User user = userService.findByName(username);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        userService.updateUser(user);
        journalEntryRepository.deleteById(id);
    }
}
