package com.CodingB.Honeypot_AI.Repository;


import com.CodingB.Honeypot_AI.Entity.ConversationSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ConversationSessionRepository extends JpaRepository<ConversationSession, String> {

    // Get session only if scam is detected
    @Query("SELECT cs FROM ConversationSession cs WHERE cs.sessionId = :sessionId AND cs.scamDetected = true")
    Optional<ConversationSession> findActiveScamSession(@Param("sessionId") String sessionId);

    // Count total sessions created today (for analytics / rate control)
    @Query("SELECT COUNT(cs) FROM ConversationSession cs WHERE cs.createdAt >= :startOfDay")
    long countTodaySessions(@Param("startOfDay") Instant startOfDay);

    // Update last updated time manually
    @Modifying
    @Query("UPDATE ConversationSession cs SET cs.lastUpdatedAt = :time WHERE cs.sessionId = :sessionId")
    void updateLastActivity(@Param("sessionId") String sessionId, @Param("time") Instant time);
}

