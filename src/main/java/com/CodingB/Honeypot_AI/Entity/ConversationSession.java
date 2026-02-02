package com.CodingB.Honeypot_AI.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationSession {

    @Id
    private String sessionId;

    private boolean scamDetected;

    private int totalMessagesExchanged;

    private Instant createdAt;

    private Instant lastUpdatedAt;
}

