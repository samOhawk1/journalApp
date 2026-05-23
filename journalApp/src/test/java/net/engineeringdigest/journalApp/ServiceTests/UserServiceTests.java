package net.engineeringdigest.journalApp.ServiceTests;

import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    //@ValueSource  -> ye bhi hai ek annotation
    @ParameterizedTest
    @CsvSource({
            "ram",
            "shyam",
            "prem"
    })
    public void testfindByUsernames(String name){
        assertNotNull(userRepository.findByUsername(name),name +" not found");
    }
    @Test
    public void testfindByUsername(){
        assertNotNull(userRepository.findByUsername("ramesh"),"username not found");
        Assertions.assertEquals(2,1+1,"not equal tha ");

    }
}
