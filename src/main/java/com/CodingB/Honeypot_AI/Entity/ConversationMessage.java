package com.CodingB.Honeypot_AI.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    private String sender;  // scammer / agent / user

    @Column(columnDefinition = "TEXT")
    private String text;

    private Instant timestamp;
}