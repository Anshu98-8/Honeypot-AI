package com.CodingB.Honeypot_AI.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuviReportingService {

    public void checkAndSendFinalReport(String sessionId) {
        log.info("Checking if final report should be sent for session {}", sessionId);

        try {
            // Later: call GUVI API when conversation finished
        } catch (Exception e) {
            log.error("Failed to send report to GUVI", e);
        }
    }
}

