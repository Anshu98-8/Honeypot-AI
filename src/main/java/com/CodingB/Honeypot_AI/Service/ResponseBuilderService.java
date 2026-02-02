package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Dto.Response.HoneypotApiResponseDto;
import com.CodingB.Honeypot_AI.Dto.Response.ScamDetectionResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResponseBuilderService {

    public HoneypotApiResponseDto buildResponse(ScamDetectionResultDto detectionResult,
                                                String agentReply,
                                                int totalMessages) {

        log.info("Building API response");

        return HoneypotApiResponseDto.builder()
                .status("success")
                .detectionResult(detectionResult)
                .agentReply(agentReply)
                .totalMessagesExchanged(totalMessages)
                .build();
    }
}
