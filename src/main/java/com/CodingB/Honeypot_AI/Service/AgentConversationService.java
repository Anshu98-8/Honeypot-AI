package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Client.OpenAiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Slf4j
public class AgentConversationService {

    private final OpenAiClient openAiClient;

    public String generateReplyFromText(String sessionId, String scammerText) {

        log.info("Generating AI honeypot reply for session {}", sessionId);

        String prompt = """
You are pretending to be a normal person who is confused about a financial issue.

Do NOT reveal you are detecting scams.
Respond like a worried user asking questions.

Scammer message:
""" + scammerText;

        return openAiClient.generateAgentReply(prompt);
    }
}
