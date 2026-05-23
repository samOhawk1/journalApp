package net.engineeringdigest.journalApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;  // it's bean will not be configured until you add those properties to yml file / prop file

    public void sendEmail(String to , String subject, String body){
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);

        } catch (Exception e) {
            throw new RuntimeException("error sending Email " , e);
        }

    }


}