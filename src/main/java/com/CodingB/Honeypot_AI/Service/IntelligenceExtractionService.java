package com.CodingB.Honeypot_AI.Service;


import com.CodingB.Honeypot_AI.Entity.IntelligenceRecord;
import com.CodingB.Honeypot_AI.Repository.IntelligenceRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class IntelligenceExtractionService {

    private final IntelligenceRecordRepository repository;

    public void extractFromText(String sessionId, String text) {

        log.info("Extracting intelligence from message for session {}", sessionId);

        if (text == null) return;

        String lower = text.toLowerCase();

        if (lower.contains("upi")) {
            save(sessionId, "SUSPICIOUS_KEYWORD", "upi");
        }
        if (lower.contains("bank")) {
            save(sessionId, "SUSPICIOUS_KEYWORD", "bank");
        }
        if (lower.contains("http")) {
            save(sessionId, "PHISHING_LINK", text);
        }
    }

    private void save(String sessionId, String type, String value) {
        IntelligenceRecord record = new IntelligenceRecord();
        record.setSessionId(sessionId);
        record.setType(type);
        record.setValue(value);

        repository.save(record);
        log.warn("Extracted {} -> {}", type, value);
    }
}

