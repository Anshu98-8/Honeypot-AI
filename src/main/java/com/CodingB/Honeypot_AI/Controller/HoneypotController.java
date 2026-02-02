package com.CodingB.Honeypot_AI.Controller;

import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import com.CodingB.Honeypot_AI.Dto.Response.HoneypotApiResponseDto;
import com.CodingB.Honeypot_AI.Service.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/honeypot")
@RequiredArgsConstructor
@Slf4j
public class HoneypotController {

    private final MessageProcessingService messageProcessingService;

    /**
     * Main endpoint that receives scam messages from hackathon platform
     */
    @PostMapping("/message")
    public ResponseEntity<HoneypotApiResponseDto> receiveMessage(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody IncomingMessageRequestDto request) {

        log.info("Received message for session: {}", request.getSessionId());

        try {
            // Basic API key validation (real validation handled in security config)
            if (apiKey == null || apiKey.isBlank()) {
                log.warn("Missing API key");
                return ResponseEntity.status(401).build();
            }

            HoneypotApiResponseDto response =
                    messageProcessingService.processIncomingMessage(request);

            log.info("Response generated successfully for session {}", request.getSessionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error while processing request", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

