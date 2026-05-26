package com.dev.journalApp.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.dev.journalApp.entity.JournalEntry;

public interface JournalEntryRepository  extends MongoRepository<JournalEntry , ObjectId> {

}
