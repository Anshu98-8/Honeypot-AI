package com.CodingB.Honeypot_AI.Dto.Request;

import lombok.Data;
import java.util.List;

@Data
public class IncomingMessageRequestDto {

    // Unique conversation session id
    private String sessionId;

    // Latest incoming message
    private MessageDto message;

    // Previous conversation history (empty for first message)
    private List<MessageDto> conversationHistory;

    // Optional metadata
    private MetadataDto metadata;
}

