package com.CodingB.Honeypot_AI.Repository;

import com.CodingB.Honeypot_AI.Entity.ScamDetectionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScamDetectionResultRepository extends JpaRepository<ScamDetectionResult, Long> {

    // Get detection result by session
    @Query("SELECT s FROM ScamDetectionResult s WHERE s.sessionId = :sessionId")
    Optional<ScamDetectionResult> findBySessionId(@Param("sessionId") String sessionId);

    // Count how many scams detected
    @Query("SELECT COUNT(s) FROM ScamDetectionResult s WHERE s.scamDetected = true")
    long countTotalDetectedScams();
}
