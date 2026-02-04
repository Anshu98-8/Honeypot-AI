package com.CodingB.Honeypot_AI.Utils;

import java.util.List;

public class PromptBuilderUtil {

    public static String buildAgentPrompt(String latestMessage, List<String> history) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a normal human, not an AI. ")
                .append("You are confused and slightly worried. ")
                .append("Never reveal you suspect a scam. ")
                .append("Keep the scammer talking and ask questions naturally.\n\n");

        if (history != null && !history.isEmpty()) {
            prompt.append("Conversation so far:\n");
            for (String msg : history) {
                prompt.append("- ").append(msg).append("\n");
            }
        }

        prompt.append("\nScammer just said: \"")
                .append(latestMessage)
                .append("\"\nRespond like a real person.");

        return prompt.toString();
    }
}

