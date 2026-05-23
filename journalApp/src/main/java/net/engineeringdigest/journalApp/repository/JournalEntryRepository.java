package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entry.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository; // Explicitly adding @Repository for clarity

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    // You can add custom query methods here if needed
}