package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Client.OpenAiClient;
import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Service
//@Slf4j
//public class AgentConversationService {
//
//    public String generateReply(IncomingMessageRequestDto request) {
//        log.info("Generating AI agent reply...");
//
//        try {
//            // In real project → call OpenAI / LLM here
//            return "Oh no, I didn't know that. What should I do now?";
//
//        } catch (Exception e) {
//            log.error("Failed to generate agent reply", e);
//            return "Sorry, I didn't understand. Can you explain again?";
//        }
//    }
//}


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentConversationService {

    private final OpenAiClient openAiClient;

    public String generateReply(IncomingMessageRequestDto request) {
        log.info("Generating AI agent reply using Azure OpenAI...");

        try {
            String scammerMessage = request.getMessage().getText();

            String prompt = "Scammer said: \"" + scammerMessage +
                    "\". Respond like a normal confused human, not an AI, " +
                    "and try to keep the conversation going.";

            return openAiClient.generateAgentReply(prompt);

        } catch (Exception e) {
            log.error("Failed to generate AI reply", e);
            return "Sorry, I didn't understand. Can you repeat?";
        }
    }
}


