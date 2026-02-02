package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IntelligenceExtractionService {

    public void extractAndStoreIntelligence(IncomingMessageRequestDto request) {
        log.info("Extracting intelligence from message...");

        try {
            String text = request.getMessage().getText();

            if (text.contains("@upi")) {
                log.warn("Potential UPI ID detected in message");
            }

            if (text.contains("http")) {
                log.warn("Possible phishing link detected");
            }

        } catch (Exception e) {
            log.error("Error extracting intelligence", e);
        }
    }
}
