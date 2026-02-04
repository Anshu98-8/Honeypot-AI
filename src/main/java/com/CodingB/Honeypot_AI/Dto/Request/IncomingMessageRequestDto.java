package com.CodingB.Honeypot_AI.Dto.Request;

import lombok.Data;
import java.util.List;

//@Data
//public class IncomingMessageRequestDto {
//
//    // Unique conversation session id
//    private String sessionId;
//
//    // Latest incoming message
//    private MessageDto message;
//
//    // Previous conversation history (empty for first message)
//    private List<MessageDto> conversationHistory;
//
//    // Optional metadata
//    private MetadataDto metadata;
//}


import lombok.Data;
import java.util.List;

@Data
public class IncomingMessageRequestDto {

    private String sessionId;
    private Message message;
    private List<HistoryMessage> conversationHistory;
    private Metadata metadata;

    @Data
    public static class Message {
        private String sender;   // "scammer" or "user"
        private String text;
        private Long timestamp;  // epoch time in milliseconds (IMPORTANT)
    }

    @Data
    public static class HistoryMessage {
        private String sender;
        private String text;
        private Long timestamp;  // epoch time in milliseconds
    }

    @Data
    public static class Metadata {
        private String channel;
        private String language;
        private String locale;
    }
}


