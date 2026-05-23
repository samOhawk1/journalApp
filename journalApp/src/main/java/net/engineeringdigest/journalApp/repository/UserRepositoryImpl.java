package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate; // by using this we can write custom queries for db

    public List<User> getUsersWithSA(){
        Query query = new Query();
        // hum chahe to ek regex method bhi hota hai isme email ko like regex bhi check kar skte hai
        Criteria criteria = new Criteria();
        // we can also add criteria without creating a Crieteria object to the query
        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),
                Criteria.where("sentimentAnalysis").is(true)));
        return mongoTemplate.find(query,User.class);
    }

    public List<User> getUserForSA() {
        return null;
    }
}
