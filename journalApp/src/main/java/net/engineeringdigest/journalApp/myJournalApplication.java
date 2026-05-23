package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement    //transactions dundhega and unke corresponding
//ek transactional context bana dega to achieve atomicity,isolation etc
public class myJournalApplication {

    public static void main(String[] args   ) {
        SpringApplication.run(myJournalApplication.class, args);
    }

    // PlatformTransactionManager interface ka implementation hai ye take hum transactions ko handle kr paye
    @Bean
    public PlatformTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }



}