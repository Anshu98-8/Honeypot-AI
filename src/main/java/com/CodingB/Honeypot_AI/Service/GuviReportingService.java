package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Client.GuviCallbackClient;
import com.CodingB.Honeypot_AI.Entity.IntelligenceRecord;
import com.CodingB.Honeypot_AI.Repository.ConversationSessionRepository;
import com.CodingB.Honeypot_AI.Repository.IntelligenceRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuviReportingService {

    private final ConversationSessionRepository sessionRepository;
    private final IntelligenceRecordRepository intelligenceRepository;
    private final GuviCallbackClient guviCallbackClient;

    /**
     * Checks if conversation has enough engagement to send final report
     */
    public void checkAndSendFinalReport(String sessionId) {

        log.info("Checking if final report is required for session {}", sessionId);

        try {
            sessionRepository.findById(sessionId).ifPresent(session -> {

                // Send report only if scam detected and enough messages exchanged
                if (session.isScamDetected() && session.getTotalMessagesExchanged() >= 6) {

                    log.info("Conditions met. Preparing final intelligence report...");

                    List<IntelligenceRecord> records =
                            intelligenceRepository.findBySessionId(sessionId);

                    Map<String, Object> payload = buildPayload(sessionId, session.getTotalMessagesExchanged(), records);

                    guviCallbackClient.sendFinalResult(payload);

                } else {
                    log.info("Report conditions not met yet for session {}", sessionId);
                }
            });

        } catch (Exception e) {
            log.error("Error while preparing GUVI report", e);
        }
    }

    /**
     * Builds JSON payload required by GUVI
     */
    private Map<String, Object> buildPayload(String sessionId, int totalMessages, List<IntelligenceRecord> records) {

        Map<String, List<String>> intelligenceMap = new HashMap<>();
        intelligenceMap.put("bankAccounts", filterByType(records, "BANK_ACCOUNT"));
        intelligenceMap.put("upiIds", filterByType(records, "UPI_ID"));
        intelligenceMap.put("phishingLinks", filterByType(records, "PHISHING_LINK"));
        intelligenceMap.put("phoneNumbers", filterByType(records, "PHONE_NUMBER"));
        intelligenceMap.put("suspiciousKeywords", filterByType(records, "SUSPICIOUS_KEYWORD"));

        Map<String, Object> payload = new HashMap<>();
        payload.put("sessionId", sessionId);
        payload.put("scamDetected", true);
        payload.put("totalMessagesExchanged", totalMessages);
        payload.put("extractedIntelligence", intelligenceMap);
        payload.put("agentNotes", "Scammer used urgency and financial fear tactics");

        log.info("Final payload prepared for session {}", sessionId);
        return payload;
    }

    private List<String> filterByType(List<IntelligenceRecord> records, String type) {
        return records.stream()
                .filter(r -> type.equalsIgnoreCase(r.getType()))
                .map(IntelligenceRecord::getValue)
                .distinct()
                .collect(Collectors.toList());
    }
}

