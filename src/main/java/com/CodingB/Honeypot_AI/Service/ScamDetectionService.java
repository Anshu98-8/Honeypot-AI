package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Client.OpenAiClient;
import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import com.CodingB.Honeypot_AI.Dto.Response.ScamDetectionResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScamDetectionService {

    private final OpenAiClient openAiClient;

    public ScamDetectionResultDto detectScam(IncomingMessageRequestDto request) {

        ScamDetectionResultDto result = new ScamDetectionResultDto();
        log.info("Detecting scam using AI...");

        try {
            String messageText = request.getMessage().getText();
            String prompt = """
                    You are an AI that classifies messages.
                    
                    Is the following message likely a scam, phishing attempt, or financial fraud?
                    
                    Answer with YES or NO.
                    
                    Message:
                    """ + messageText;


            String aiResponse = openAiClient.generateAgentReply(prompt).trim().toUpperCase();

            log.info("AI scam detection raw response: {}", aiResponse);

            if (aiResponse.startsWith("YES")) {
                result.setScamDetected(true);
                result.setConfidenceScore(90);
                result.setReason("AI flagged message as scam");
                return result;
            }

        } catch (Exception e) {
            log.error("AI scam detection failed", e);
        }

        // 🔁 FALLBACK KEYWORD DETECTION (IMPORTANT)
        String lowerText = request.getMessage().getText().toLowerCase();
        if (lowerText.contains("upi") || lowerText.contains("verify") || lowerText.contains("account blocked") || lowerText.contains("urgent") || lowerText.contains("suspend")) {

            result.setScamDetected(true);
            result.setConfidenceScore(75);
            result.setReason("Keyword-based scam detection triggered");
        } else {
            result.setScamDetected(false);
            result.setConfidenceScore(30);
            result.setReason("No strong scam indicators");
        }

        return result;
    }

    // 🔥 Simple keyword-based quick detection
    public boolean quickKeywordDetect(String text) {
        if (text == null) return false;

        String lower = text.toLowerCase();

        boolean result = lower.contains("upi") || lower.contains("account blocked") || lower.contains("verify") || lower.contains("suspend") || lower.contains("urgent") || lower.contains("bank");

        log.info("Keyword scam detection result: {}", result);
        return result;
    }

}
