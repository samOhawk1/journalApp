package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entry.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.entry.JournalEntry;
import net.engineeringdigest.journalApp.entry.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
    // You can add custom query methods here if needed
}