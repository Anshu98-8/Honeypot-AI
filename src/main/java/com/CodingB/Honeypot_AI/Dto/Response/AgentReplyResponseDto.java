package com.CodingB.Honeypot_AI.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentReplyResponseDto {

    private String status;   // "success"
    private String reply;    // AI generated reply text
}

