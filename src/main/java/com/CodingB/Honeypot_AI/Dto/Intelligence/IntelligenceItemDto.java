package com.CodingB.Honeypot_AI.Dto.Intelligence;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IntelligenceItemDto {

    // Example: "UPI_ID", "BANK_ACCOUNT"
    private String type;

    private String value;
}

