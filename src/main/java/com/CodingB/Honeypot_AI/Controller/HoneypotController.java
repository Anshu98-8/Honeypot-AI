package com.CodingB.Honeypot_AI.Controller;


import com.CodingB.Honeypot_AI.Service.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/honeypot")
@RequiredArgsConstructor
@Slf4j
public class HoneypotController {

    private final MessageProcessingService messageProcessingService;

    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> receiveMessage(@RequestHeader("x-api-key") String apiKey, @RequestBody Map<String, Object> body) {
        log.info("📩 Incoming honeypot request received");

        try {
            // ---------------- SESSION ID ----------------
            String sessionId = (String) body.get("sessionId");
            if (sessionId == null || sessionId.isBlank()) {
                return badRequest("sessionId is required");
            }

            // ---------------- MESSAGE ----------------
            Map<String, Object> message = (Map<String, Object>) body.get("message");
            if (message == null) {
                return badRequest("message object is required");
            }

            String sender = (String) message.get("sender");
            String text = (String) message.get("text");
            Long timestamp = parseTimestamp(message.get("timestamp"));

            if (sender == null || text == null) {
                return badRequest("message.sender and message.text are required");
            }

            // ---------------- CONVERSATION HISTORY ----------------
            List<Map<String, Object>> conversationHistory = (List<Map<String, Object>>) body.getOrDefault("conversationHistory", List.of());

            // ---------------- METADATA ----------------
            Map<String, Object> metadata = (Map<String, Object>) body.getOrDefault("metadata", Map.of());

            String channel = (String) metadata.getOrDefault("channel", "UNKNOWN");
            String language = (String) metadata.getOrDefault("language", "UNKNOWN");
            String locale = (String) metadata.getOrDefault("locale", "UNKNOWN");

            log.info("Session: {}, Sender: {}, Channel: {}", sessionId, sender, channel);

            // ---------------- CALL SERVICE ----------------
            Map<String, Object> serviceResponse = messageProcessingService.processIncomingMessage(sessionId, sender, text, timestamp, conversationHistory, channel, language, locale);

            return ResponseEntity.ok(serviceResponse);

        } catch (Exception e) {
            log.error("❌ Error processing honeypot request", e);
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", "Internal server error"));
        }
    }

    // ---------------------------------------------------------
    // 🕒 TIMESTAMP PARSER (Handles ALL formats)
    // ---------------------------------------------------------
    private Long parseTimestamp(Object tsObj) {
        try {
            if (tsObj == null) return System.currentTimeMillis();

            if (tsObj instanceof Number num) {
                return num.longValue();
            }

            if (tsObj instanceof String tsStr) {
                // Epoch string
                if (tsStr.matches("\\d+")) {
                    return Long.parseLong(tsStr);
                }
                // ISO date string
                return Instant.parse(tsStr).toEpochMilli();
            }

        } catch (Exception e) {
            log.warn("Invalid timestamp format received. Using current time.");
        }
        return System.currentTimeMillis();
    }

    // ---------------------------------------------------------
    // ❌ BAD REQUEST HELPER
    // ---------------------------------------------------------
    private ResponseEntity<Map<String, Object>> badRequest(String msg) {
        return ResponseEntity.badRequest().body(Map.of("status", "error", "message", msg));
    }
}
