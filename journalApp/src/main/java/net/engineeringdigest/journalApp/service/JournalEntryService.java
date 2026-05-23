package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entry.JournalEntry;
import net.engineeringdigest.journalApp.entry.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional  // treat it like a transaction
    public void saveEntry(JournalEntry journalEntry, String userName ) {
        try {
            User user = userService.findByUsername(userName);
            if (user == null) {
                throw new IllegalArgumentException("User not found: " + userName);
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.save(user);
        } catch (Exception e) {

            throw new RuntimeException("Unexpected error occurred at runtime\n" , e); // Improved error message
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(ObjectId myid) {
        Optional<JournalEntry> entry = journalEntryRepository.findById(myid);
        return entry.orElse(null); //simplified
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id, String userName) {
       try{
           User user = userService.findByUsername(userName);
           if (user == null) {
               throw new IllegalArgumentException("User not found: " + userName);
           }
           boolean removed = user.getJournalEntries().removeIf(x->x.getId().equals(id)); // remove from journalentry list from userdb jounalentry list
           if(removed) {
               userService.save(user); // isse changes (aka deleted entries ka khata update ho jayega in users db )
               journalEntryRepository.deleteById(id); // toh isse journalEntries db se delete hogi
           }
           return removed;
       }
       catch (Exception e){
           throw new RuntimeException("An error occcured while saving the entry " , e);
       }
    }
}
