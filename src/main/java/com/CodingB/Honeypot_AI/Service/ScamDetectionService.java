package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import com.CodingB.Honeypot_AI.Dto.Response.ScamDetectionResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScamDetectionService {

    public ScamDetectionResultDto detectScam(IncomingMessageRequestDto request) {
        log.info("Running scam detection...");

        ScamDetectionResultDto result = new ScamDetectionResultDto();

        try {
            String text = request.getMessage().getText().toLowerCase();

            if (text.contains("verify") || text.contains("urgent")
                    || text.contains("account blocked") || text.contains("upi")) {

                result.setScamDetected(true);
                result.setConfidenceScore(85);
                result.setReason("Message contains urgency and financial keywords");

                log.warn("Scam detected with high confidence");
            } else {
                result.setScamDetected(false);
                result.setConfidenceScore(20);
                result.setReason("No strong scam indicators");
            }

        } catch (Exception e) {
            log.error("Error during scam detection", e);
            result.setScamDetected(false);
            result.setConfidenceScore(0);
            result.setReason("Detection failed due to error");
        }

        return result;
    }
}
