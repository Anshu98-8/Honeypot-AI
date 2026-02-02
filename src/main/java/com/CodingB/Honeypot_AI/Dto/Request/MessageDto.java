package com.CodingB.Honeypot_AI.Dto.Request;

import lombok.Data;
import java.time.Instant;

@Data
public class MessageDto {

    // Who sent the message: "scammer" or "user"
    private String sender;

    // Actual text message
    private String text;

    // Time when the message was sent
    private Instant timestamp;
}

