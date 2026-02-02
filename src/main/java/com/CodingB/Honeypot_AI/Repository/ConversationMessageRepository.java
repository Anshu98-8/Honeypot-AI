package com.CodingB.Honeypot_AI.Repository;

import com.CodingB.Honeypot_AI.Entity.ConversationMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, Long> {

    // Get full conversation ordered by time
    @Query("SELECT m FROM ConversationMessage m WHERE m.sessionId = :sessionId ORDER BY m.timestamp ASC")
    List<ConversationMessage> getConversationHistory(@Param("sessionId") String sessionId);

    // Count total messages in a session
    @Query("SELECT COUNT(m) FROM ConversationMessage m WHERE m.sessionId = :sessionId")
    int countMessagesInSession(@Param("sessionId") String sessionId);

    // Delete very old conversations (cleanup job)
    @Modifying
    @Query(value = "DELETE FROM conversation_message WHERE timestamp < NOW() - INTERVAL '30 days'", nativeQuery = true)
    void deleteOldMessages();
}

