package com.CodingB.Honeypot_AI.Dto.Response;

import lombok.Data;

@Data
public class ScamDetectionResultDto {

    private boolean scamDetected;

    // Confidence score (0–100)
    private int confidenceScore;

    // Reason why flagged as scam
    private String reason;
}

