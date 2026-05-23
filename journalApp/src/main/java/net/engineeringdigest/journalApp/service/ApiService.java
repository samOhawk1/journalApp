package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entry.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    public Quote fetchDataWithApiKeyInHeader(String apiUrl, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Quote>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Quote>>() {}
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
            return response.getBody().get(0); // Return the first (and likely only) Quote object in the list
        } else {
            return new Quote(); // Or handle the error case appropriately, e.g., return null or throw an exception
        }
    }
}