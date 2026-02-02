package com.CodingB.Honeypot_AI.Dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HoneypotApiResponseDto {

    private String status; // success / failure

    private ScamDetectionResultDto detectionResult;

    private String agentReply;

    private int totalMessagesExchanged;
}

