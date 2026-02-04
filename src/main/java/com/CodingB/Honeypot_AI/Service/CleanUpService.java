package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Repository.ConversationMessageRepository;
import com.CodingB.Honeypot_AI.Repository.IntelligenceRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CleanUpService {

    private final ConversationMessageRepository messageRepository;
    private final IntelligenceRecordRepository intelligenceRepository;

    // Runs once every day
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanOldData() {
        log.info("Running scheduled cleanup job...");

        try {
            messageRepository.deleteOldMessages();
            intelligenceRepository.removeDuplicateRecords();
            log.info("Cleanup completed successfully");
        } catch (Exception e) {
            log.error("Cleanup job failed", e);
        }
    }
}

