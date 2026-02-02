package com.CodingB.Honeypot_AI.Dto.Intelligence;

import lombok.Data;
import java.util.List;

@Data
public class ExtractedIntelligenceDto {

    private List<String> bankAccounts;
    private List<String> upiIds;
    private List<String> phishingLinks;
    private List<String> phoneNumbers;
    private List<String> suspiciousKeywords;
}

