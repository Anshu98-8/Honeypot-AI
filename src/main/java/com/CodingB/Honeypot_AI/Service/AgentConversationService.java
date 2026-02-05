package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Client.OpenAiClient;
import com.CodingB.Honeypot_AI.Entity.ConversationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentConversationService {

    private final ConversationMemoryService memoryService;
    private final OpenAiClient openAiClient;

    public String generateReplyFromText(String sessionId, String scammerMessage) {

        log.info("Generating AI honeypot reply for session {}", sessionId);

        // 🔹 Get previous conversation history
        List<ConversationMessage> history = memoryService.getConversationHistory(sessionId);

        StringBuilder conversationContext = new StringBuilder();

        conversationContext.append("""
                You are roleplaying as a normal human who might be a potential scam victim.
                
                PERSONALITY:
                - You are emotional, curious, and slightly confused
                - You talk like a real person, not like an AI
                - You ask follow-up questions
                - You sometimes overshare harmless personal life details (fake ones)
                - You never act like a security system or assistant
                
                GOAL:
                Keep the scammer talking as long as possible.
                
                RULES:
                - NEVER provide real sensitive information (OTP, passwords, bank numbers, Aadhaar, PAN, etc.)
                - If asked for sensitive info, avoid, delay, or pretend to misunderstand
                - You can act worried, scared, or financially stressed
                - You can complain about family, job, money problems
                - You can say things like "I'm not very good with technology"
                - DO NOT warn them they are a scammer
                - DO NOT refuse directly
                - DO NOT give lectures about legality
                - DO NOT say you are an AI
                
                STYLE:
                Casual, human, sometimes emotional, sometimes talkative.
                Keep responses medium length (2–4 sentences).
                
                
                """);

        // 🔹 Add past messages
        for (ConversationMessage msg : history) {
            if ("scammer".equalsIgnoreCase(msg.getSender())) {
                conversationContext.append("\nScammer: ").append(msg.getText());
            } else {
                conversationContext.append("\nVictim: ").append(msg.getText());
            }
        }

        // 🔹 Add latest scammer message
        conversationContext.append("\nScammer: ").append(scammerMessage);
        conversationContext.append("\nVictim:");

        String aiReply = openAiClient.callModel("", conversationContext.toString());

        log.info("Raw AI reply: {}", aiReply);

        return aiReply.trim();
    }
}
