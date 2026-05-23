package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entry.JournalEntry;
import net.engineeringdigest.journalApp.entry.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();

    public void save(User userEntry) {
        userRepository.save(userEntry);
    }

    // slf4j is abstraction above logback
//    Logger logger = LoggerFactory.getLogger(UserService.class);  iski jagah hum slf4j ka annotation daldete hai jo ise replace karega and then replce logger with log

    public void saveNewUser(User  userEntry) {
       try{
           String encodedPassword = passwordEncoder.encode(userEntry.getPassword());
           userEntry.setPassword(encodedPassword);
           userEntry.setRoles(Arrays.asList("USER"));
           userRepository.save(userEntry);
       }
       catch (Exception e){
           log.info("hahahahahhahhah");
           throw new RuntimeException("saveNewUser faced error at runtime",e);
       }

    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(ObjectId  myid) {
        return userRepository.findById(myid).orElse(null);
    }

    public void deleteById(ObjectId  id) {
        userRepository.deleteById(id);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);

    }

    public void saveAdmin(User userEntry) {
        String encodedPassword = passwordEncoder.encode(userEntry.getPassword());
        userEntry.setPassword(encodedPassword);
        userEntry.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(userEntry);
    }
}