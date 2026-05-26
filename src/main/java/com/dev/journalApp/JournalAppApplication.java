package com.dev.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAppApplication.class, args);
	}



/*	// PlatformTransactionManger
	//MongoTransactionManger  */
	@Bean
	public PlatformTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
		return new MongoTransactionManager(mongoDatabaseFactory);
	}






}

