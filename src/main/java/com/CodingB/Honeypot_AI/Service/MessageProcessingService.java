//package com.CodingB.Honeypot_AI.Service;
//
//import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
//import com.CodingB.Honeypot_AI.Dto.Response.HoneypotApiResponseDto;
//import com.CodingB.Honeypot_AI.Dto.Response.ScamDetectionResultDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MessageProcessingService {
//
//    private final ScamDetectionService scamDetectionService;
//    private final AgentConversationService agentConversationService;
//    private final IntelligenceExtractionService intelligenceExtractionService;
//    private final ConversationMemoryService memoryService;
//    private final GuviReportingService guviReportingService;
//    private final ResponseBuilderService responseBuilderService;
//
//    /**
//     * Main flow controller for every incoming message
//     */
//    public HoneypotApiResponseDto processIncomingMessage(IncomingMessageRequestDto request) {
//
//        String sessionId = request.getSessionId();
//        log.info("Processing message for session {}", sessionId);
//
//        try {
//            // 1️⃣ Save incoming message
//            memoryService.saveIncomingMessage(request);
//
//            // 2️⃣ Detect scam
//            ScamDetectionResultDto detectionResult =
//                    scamDetectionService.detectScam(request);
//
//            String agentReply;
//
//            if (detectionResult.isScamDetected()) {
//                log.warn("Scam detected for session {}", sessionId);
//
//                // Mark session as scam
//                memoryService.markScamDetected(sessionId);
//
//                // 3️⃣ Generate AI reply
//                agentReply = agentConversationService.generateReplyFromText(request);
//
//                // 4️⃣ Extract intelligence
//                intelligenceExtractionService.extractFromText(request);
//
//            } else {
//                log.info("No scam detected yet for session {}", sessionId);
//                agentReply = "Okay, can you explain more?";
//            }
//
//            // 5️⃣ Save agent reply
//            memoryService.saveAgentReply(sessionId, agentReply);
//
//            // 6️⃣ Check if final GUVI report should be sent
//            guviReportingService.checkAndSendFinalReport(sessionId);
//
//            // 7️⃣ Build API response
//            return responseBuilderService.buildResponse(
//                    detectionResult,
//                    agentReply,
//                    memoryService.countMessages(sessionId)
//            );
//
//        } catch (Exception e) {
//            log.error("Error processing message for session " + sessionId, e);
//            throw new RuntimeException("Failed to process message");
//        }
//    }
//
//    public String processRawMessage(String sessionId, String sender, String text, Long timestamp) {
//
//        log.info("Processing raw message for session {}", sessionId);
//
//        try {
//            // Save scammer message
//            memoryService.saveIncomingRawMessage(sessionId, sender, text, timestamp);
//
//            // Detect scam
//            boolean isScam = scamDetectionService.quickKeywordDetect(text);
//
//            String reply;
//
//            if (isScam) {
//                memoryService.markScamDetected(sessionId);
//                reply = agentConversationService.generateReplyFromText(sessionId, text);
//                intelligenceExtractionService.extractFromText(sessionId, text);
//            } else {
//                reply = "Can you explain more about that?";
//            }
//
//            memoryService.saveAgentReply(sessionId, reply);
//            guviReportingService.checkAndSendFinalReport(sessionId);
//
//            return reply;
//
//        } catch (Exception e) {
//            log.error("Error in raw message processing", e);
//            return "I didn't understand, can you clarify?";
//        }
//    }
//
//}


package com.CodingB.Honeypot_AI.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProcessingService {

    private final ScamDetectionService scamDetectionService;
    private final AgentConversationService agentConversationService;
    private final IntelligenceExtractionService intelligenceExtractionService;
    private final ConversationMemoryService memoryService;
    private final GuviReportingService guviReportingService;

    /**
     * 🔥 MAIN METHOD USED BY CONTROLLER (Hackathon Flow)
     */
    public Map<String, Object> processIncomingMessage(
            String sessionId,
            String sender,
            String text,
            Long timestamp,
            List<Map<String, Object>> conversationHistory,
            String channel,
            String language,
            String locale
    ) {

        log.info("🚀 Processing message for session {}", sessionId);

        Map<String, Object> response = new HashMap<>();

        try {
            // 1️⃣ Save incoming scammer message
            memoryService.saveIncomingRawMessage(sessionId, sender, text, timestamp);

            // 2️⃣ Scam detection (fast keyword check)
            boolean isScam = scamDetectionService.quickKeywordDetect(text);

            String reply;

            if (isScam) {
                log.warn("⚠ Scam detected for session {}", sessionId);

                memoryService.markScamDetected(sessionId);

                // 3️⃣ Generate honeypot human-like reply
                reply = agentConversationService.generateReplyFromText(sessionId, text);

                // 4️⃣ Extract intelligence
                intelligenceExtractionService.extractFromText(sessionId, text);

            } else {
                reply = "I'm not sure I understand. Can you explain more?";
            }

            // 5️⃣ Save agent reply
            memoryService.saveAgentReply(sessionId, reply);

            // 6️⃣ Check if final GUVI report should be sent
            guviReportingService.checkAndSendFinalReport(sessionId);

            // 7️⃣ Build hackathon response format
            response.put("status", "success");
            response.put("reply", reply);

            return response;

        } catch (Exception e) {
            log.error("❌ Error processing message", e);

            response.put("status", "error");
            response.put("reply", "Sorry, something went wrong.");
            return response;
        }
    }
}
