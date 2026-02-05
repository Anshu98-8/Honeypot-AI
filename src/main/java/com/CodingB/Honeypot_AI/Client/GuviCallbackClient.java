package com.CodingB.Honeypot_AI.Client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GuviCallbackClient {

    private final RestTemplate restTemplate;

    @Value("${guvi.callback.url}")
    private String guviUrl;

    public void sendFinalResult(Map<String, Object> payload) {
        log.info("Sending final intelligence report to GUVI...");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.exchange(guviUrl, HttpMethod.POST, requestEntity, String.class);

            log.info("GUVI callback response status: {}", response.getStatusCode());

        } catch (Exception e) {
            log.error("Error sending final report to GUVI", e);
        }
    }
}

