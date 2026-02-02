package com.CodingB.Honeypot_AI.Repository;

import com.CodingB.Honeypot_AI.Entity.IntelligenceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntelligenceRecordRepository extends JpaRepository<IntelligenceRecord, Long> {

    // Get all intelligence for one session
    @Query("SELECT i FROM IntelligenceRecord i WHERE i.sessionId = :sessionId")
    List<IntelligenceRecord> findBySessionId(@Param("sessionId") String sessionId);

    // Count how many UPI IDs we captured
    @Query("SELECT COUNT(i) FROM IntelligenceRecord i WHERE i.type = 'UPI_ID'")
    long countTotalUpiCaptured();

    // Delete duplicate intelligence entries
    @Modifying
    @Query(value = """
        DELETE FROM intelligence_record a
        USING intelligence_record b
        WHERE a.id < b.id
        AND a.session_id = b.session_id
        AND a.type = b.type
        AND a.value = b.value
    """, nativeQuery = true)
    void removeDuplicateRecords();
}
