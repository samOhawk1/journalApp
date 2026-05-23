package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entry.Quote;
import net.engineeringdigest.journalApp.entry.User;
import net.engineeringdigest.journalApp.service.ApiService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppCache app_cache;
    @Value("${external.weather-api.api-key}")
    private String apikey;
    @Autowired
    private ApiService apiService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @GetMapping("/abc")
    public String getQuote(){
        String url = app_cache.APP_CACHE.get("weather_api"); // Access APP_CACHE here
        return apiService.fetchDataWithApiKeyInHeader(url, apikey).getQuote();
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        app_cache.init();
    }

}