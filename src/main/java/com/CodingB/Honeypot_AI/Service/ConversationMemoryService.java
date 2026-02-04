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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationMemoryService {

    private final ConversationSessionRepository sessionRepository;
    private final ConversationMessageRepository messageRepository;

    /**
     * Save incoming scammer message and create session if first time
     */
    public void saveIncomingMessage(IncomingMessageRequestDto request) {
        String sessionId = request.getSessionId();
        log.info("Saving incoming message for session {}", sessionId);

        try {
            // Check if session exists
            Optional<ConversationSession> sessionOpt = sessionRepository.findById(sessionId);

            ConversationSession session;
            if (sessionOpt.isEmpty()) {
                log.info("Creating new conversation session {}", sessionId);

                session = new ConversationSession();
                session.setSessionId(sessionId);
                session.setScamDetected(false);
                session.setTotalMessagesExchanged(0);
                session.setCreatedAt(Instant.now());
                session.setLastUpdatedAt(Instant.now());

                sessionRepository.save(session);
            } else {
                session = sessionOpt.get();
                session.setLastUpdatedAt(Instant.now());
                sessionRepository.save(session);
            }

            // Save message
            ConversationMessage message = new ConversationMessage();
            message.setSessionId(sessionId);
            message.setSender(request.getMessage().getSender());
            message.setText(request.getMessage().getText());
            message.setTimestamp(request.getMessage().getTimestamp());

            messageRepository.save(message);

        } catch (Exception e) {
            log.error("Error saving incoming message", e);
        }
    }

    /**
     * Save AI agent reply
     */
    public void saveAgentReply(String sessionId, String reply) {
        log.info("Saving agent reply for session {}", sessionId);

        try {
            ConversationMessage message = new ConversationMessage();
            message.setSessionId(sessionId);
            message.setSender("agent");
            message.setText(reply);
            message.setTimestamp(Instant.now());

            messageRepository.save(message);

            // Update session message count
            updateMessageCount(sessionId);

        } catch (Exception e) {
            log.error("Error saving agent reply", e);
        }
    }

    /**
     * Count total messages exchanged in a session
     */
    public int countMessages(String sessionId) {
        try {
            int count = messageRepository.countMessagesInSession(sessionId);
            log.info("Total messages in session {} = {}", sessionId, count);
            return count;
        } catch (Exception e) {
            log.error("Error counting messages", e);
            return 0;
        }
    }

    /**
     * Update total message count in session table
     */
    private void updateMessageCount(String sessionId) {
        try {
            int total = messageRepository.countMessagesInSession(sessionId);

            Optional<ConversationSession> sessionOpt = sessionRepository.findById(sessionId);
            sessionOpt.ifPresent(session -> {
                session.setTotalMessagesExchanged(total);
                session.setLastUpdatedAt(Instant.now());
                sessionRepository.save(session);
            });

        } catch (Exception e) {
            log.error("Error updating message count", e);
        }
    }
}
