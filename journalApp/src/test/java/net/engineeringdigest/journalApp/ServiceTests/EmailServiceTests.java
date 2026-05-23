package net.engineeringdigest.journalApp.ServiceTests;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

@SpringBootTest
@Slf4j
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    @Async
    public void sendMail(){
        for(int i=1;i<1000;i++) {
            emailService.sendEmail("xyz@gmail.com", "lmao lmao lmao lmao",
                    "what can you even do ? shudddup . SWlALALALA");
        }
    }
}