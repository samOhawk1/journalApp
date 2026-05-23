package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entry.JournalEntry;
import net.engineeringdigest.journalApp.entry.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            List<Sentiment> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimetCounts = new HashMap<>();

            for(Sentiment sentiment : filteredEntries){
                if(sentiment != null){
                    sentimetCounts.put(sentiment,sentimetCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for(Map.Entry<Sentiment,Integer> entry : sentimetCounts.entrySet()) {
               if(maxCount < entry.getValue()){
                   maxCount = entry.getValue();
                   mostFrequentSentiment = entry.getKey();
               }
            }

            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(),"last 7 day sentiment according to the journals",mostFrequentSentiment.toString());
            }
        }
    }


    @Scheduled(cron = "0 0/10 * ? * *")
    public void fetchUpdatedAppCache(){ // UPDATING CACHE EVERY 10 MINUTES
        appCache.init();
    }
}
