package com.CodingB.Honeypot_AI.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntelligenceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    // BANK_ACCOUNT / UPI_ID / LINK / PHONE
    private String type;

    private String value;
}

