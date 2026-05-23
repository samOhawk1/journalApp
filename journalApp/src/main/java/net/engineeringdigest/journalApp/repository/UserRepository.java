package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entry.JournalEntry;
import net.engineeringdigest.journalApp.entry.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {
    // You can add custom query methods here if needed
    User findByUsername (String username);

    void deleteByUsername(String username);
}