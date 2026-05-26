package com.dev.journalApp.entity;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data
public class JournalEntry {

//    User journalUser;


    @Id
    private ObjectId id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private LocalDateTime date;
    private ObjectId journalUserId;
}


//    public LocalDateTime getDate() {
//        return date;
//    }
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }
//    public ObjectId getId() {
//        return id;
//    }
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//    public String getContent() {
//        return content;
//    }
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    @Override
//    public String toString() {
//        return "JournalEntry{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", date=" + date +
//                '}';
//    }
//}
//