package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import com.CodingB.Honeypot_AI.Entity.ConversationMessage;
import com.CodingB.Honeypot_AI.Entity.ConversationSession;
import com.CodingB.Honeypot_AI.Repository.ConversationMessageRepository;
import com.CodingB.Honeypot_AI.Repository.ConversationSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationMemoryService {

    private final ConversationSessionRepository sessionRepository;
    private final ConversationMessageRepository messageRepository;

    public void saveIncomingMessage(IncomingMessageRequestDto request) {
        String sessionId = request.getSessionId();
        log.info("Saving incoming message for session {}", sessionId);

        try {
            ConversationSession session = sessionRepository.findById(sessionId).orElseGet(() -> {
                ConversationSession newSession = new ConversationSession();
                newSession.setSessionId(sessionId);
                newSession.setScamDetected(false);
                newSession.setTotalMessagesExchanged(0);
                newSession.setCreatedAt(Instant.now());
                newSession.setLastUpdatedAt(Instant.now());
                return sessionRepository.save(newSession);
            });

            session.setLastUpdatedAt(Instant.now());
            sessionRepository.save(session);

            ConversationMessage msg = new ConversationMessage();
            msg.setSessionId(sessionId);
            msg.setSender(request.getMessage().getSender());
            msg.setText(request.getMessage().getText());

            // 🔥 Convert epoch millis → Instant
            msg.setTimestamp(Instant.ofEpochMilli(request.getMessage().getTimestamp()));

            messageRepository.save(msg);
            updateMessageCount(sessionId);

        } catch (Exception e) {
            log.error("Error saving incoming message", e);
        }
    }

    public void saveAgentReply(String sessionId, String reply) {
        log.info("Saving agent reply for session {}", sessionId);

        try {
            ConversationMessage msg = new ConversationMessage();
            msg.setSessionId(sessionId);
            msg.setSender("agent");
            msg.setText(reply);
            msg.setTimestamp(Instant.now());

            messageRepository.save(msg);
            updateMessageCount(sessionId);

        } catch (Exception e) {
            log.error("Error saving agent reply", e);
        }
    }

    public void markScamDetected(String sessionId) {
        sessionRepository.findById(sessionId).ifPresent(session -> {
            session.setScamDetected(true);
            session.setLastUpdatedAt(Instant.now());
            sessionRepository.save(session);
            log.warn("Session {} marked as scam", sessionId);
        });
    }

    private void updateMessageCount(String sessionId) {
        int total = messageRepository.countMessagesInSession(sessionId);
        sessionRepository.findById(sessionId).ifPresent(session -> {
            session.setTotalMessagesExchanged(total);
            session.setLastUpdatedAt(Instant.now());
            sessionRepository.save(session);
        });
    }

    public List<ConversationMessage> getConversationHistory(String sessionId) {
        return messageRepository.getConversationHistory(sessionId);
    }

    public int countMessages(String sessionId) {
        return messageRepository.countMessagesInSession(sessionId);
    }

    public void saveIncomingRawMessage(String sessionId, String sender, String text, Long timestamp) {
        ConversationMessage msg = new ConversationMessage();
        msg.setSessionId(sessionId);
        msg.setSender(sender);
        msg.setText(text);
        msg.setTimestamp(Instant.ofEpochMilli(timestamp));

        messageRepository.save(msg);
        updateMessageCount(sessionId);
    }


}



