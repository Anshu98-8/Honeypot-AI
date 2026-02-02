package com.CodingB.Honeypot_AI.Dto.Request;

import lombok.Data;

@Data
public class MetadataDto {

    // SMS / WhatsApp / Email / Chat
    private String channel;

    // Language of message
    private String language;

    // Country or region (ex: IN)
    private String locale;
}

