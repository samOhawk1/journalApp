package net.engineeringdigest.journalApp.cache;
import net.engineeringdigest.journalApp.entry.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache  {
    public Map<String,String> APP_CACHE; // Initialize APP_CACHE here

    @Autowired
    public ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all= configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity c : all) {
            APP_CACHE.put(c.getKey(), c.getValue());
        }
    }

}