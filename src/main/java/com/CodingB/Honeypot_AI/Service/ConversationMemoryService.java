package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConversationMemoryService {

    public void saveIncomingMessage(IncomingMessageRequestDto request) {
        log.info("Saving incoming scammer message for session {}", request.getSessionId());
    }

    public void saveAgentReply(String sessionId, String reply) {
        log.info("Saving agent reply for session {}", sessionId);
    }

    public int countMessages(String sessionId) {
        log.info("Counting messages for session {}", sessionId);
        return 5; // temporary mock
    }
}

