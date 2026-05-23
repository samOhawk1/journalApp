package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entry.JournalEntry;
import net.engineeringdigest.journalApp.entry.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    // Get all journal entries
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> journalList = user.getJournalEntries();
        if(journalList != null && !journalList.isEmpty()) {
            return new ResponseEntity<>(journalList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new journal entry
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {

       try{
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String username = authentication.getName();
           journalEntryService.saveEntry(myEntry,username);
           return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    // Get a journal entries of a user by ID
    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = Optional.ofNullable(journalEntryService.getEntryById(myid));
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // Delete a journal entry by ID
    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteEntryById(myid,userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 means successful delete with no body
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     //Update a journal entry by ID
    @PutMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = authentication.getName(); //get user name from authentication

        JournalEntry old = journalEntryService.getEntryById(myid);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old); //beacuse old entry has been updated
            return new ResponseEntity<>(old, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
