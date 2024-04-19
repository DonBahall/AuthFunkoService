package com.example.authfunkoservice.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class WakeUpServiceConfig {
    private final Logger log = LoggerFactory.getLogger(WakeUpServiceConfig.class);

    @Scheduled(fixedRate = 300000)
    public void keepAlive() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String graphqlQuery = "{ \"query\": \"{ Wakeup { wakeup } }\" }";


        String graphqlUrl = "https://funkopop.onrender.com/graphql";

        HttpEntity<String> request = new HttpEntity<>(graphqlQuery, headers);

         new RestTemplate().exchange(
                graphqlUrl,
                HttpMethod.POST,
                request,
                String.class
        );
         log.info("This request send for wake up free hosting, ignore it");
    }
}
