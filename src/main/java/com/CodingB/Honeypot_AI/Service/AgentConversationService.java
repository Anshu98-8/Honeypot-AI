package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AgentConversationService {

    public String generateReply(IncomingMessageRequestDto request) {
        log.info("Generating AI agent reply...");

        try {
            // In real project → call OpenAI / LLM here
            return "Oh no, I didn't know that. What should I do now?";

        } catch (Exception e) {
            log.error("Failed to generate agent reply", e);
            return "Sorry, I didn't understand. Can you explain again?";
        }
    }
}

