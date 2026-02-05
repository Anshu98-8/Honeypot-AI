package com.CodingB.Honeypot_AI.Client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiClient {

    private final ChatModel chatModel; // Spring AI injects Azure model automatically

    public String generateAgentReply(String promptText) {
        log.info("Calling Azure OpenAI via Spring AI...");

        try {
            Prompt prompt = new Prompt(promptText);
            String response = chatModel.call(prompt).getResult().getOutput().getText();

            log.info("Received AI response");
            return response.trim();

        } catch (Exception e) {
            log.error("Error calling Azure OpenAI", e);
            return "Sorry, I didn’t understand that. Can you explain again?";
        }
    }

    /**
     * Calls Azure OpenAI and returns ONLY the assistant text reply
     */
    public String callModel(String systemPrompt, String userMessage) {

        log.info("Calling Azure OpenAI via Spring AI...");

        try {
            // Combine system + user message into one prompt
            String fullPrompt = systemPrompt + "\nUser: " + userMessage;

            // In Spring AI, this already returns String
            String aiReply = chatModel.call(fullPrompt);

            log.info("Received AI response");

            return aiReply;

        } catch (Exception e) {
            log.error("Error calling AI model", e);
            return "Sorry… I didn’t understand that. Can you explain again?";
        }
    }

}
