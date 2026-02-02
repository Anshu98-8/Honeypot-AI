package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import com.CodingB.Honeypot_AI.Dto.Response.HoneypotApiResponseDto;
import com.CodingB.Honeypot_AI.Dto.Response.ScamDetectionResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProcessingService {

    private final ScamDetectionService scamDetectionService;
    private final AgentConversationService agentConversationService;
    private final IntelligenceExtractionService intelligenceExtractionService;
    private final ConversationMemoryService memoryService;
    private final GuviReportingService guviReportingService;
    private final ResponseBuilderService responseBuilderService;

    public HoneypotApiResponseDto processIncomingMessage(IncomingMessageRequestDto request) {
        log.info("Processing new message for session: {}", request.getSessionId());

        try {
            // Step 1: Save incoming message
            memoryService.saveIncomingMessage(request);

            // Step 2: Detect scam
            ScamDetectionResultDto detectionResult =
                    scamDetectionService.detectScam(request);

            // Step 3: If scam detected → AI Agent replies
            String agentReply = null;
            if (detectionResult.isScamDetected()) {
                log.info("Scam detected. Activating AI agent...");
                agentReply = agentConversationService.generateReply(request);
                intelligenceExtractionService.extractAndStoreIntelligence(request);
            } else {
                log.info("No scam detected yet. Sending safe neutral reply.");
                agentReply = "Okay, can you explain more?";
            }

            // Step 4: Update memory
            memoryService.saveAgentReply(request.getSessionId(), agentReply);

            // Step 5: Check if conversation is finished and report
            guviReportingService.checkAndSendFinalReport(request.getSessionId());

            // Step 6: Build API response
            return responseBuilderService.buildResponse(
                    detectionResult,
                    agentReply,
                    memoryService.countMessages(request.getSessionId())
            );

        } catch (Exception e) {
            log.error("Error processing message", e);
            throw new RuntimeException("Failed to process message");
        }
    }
}
