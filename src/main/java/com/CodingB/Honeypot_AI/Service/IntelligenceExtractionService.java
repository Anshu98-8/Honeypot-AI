package com.CodingB.Honeypot_AI.Service;

import com.CodingB.Honeypot_AI.Config.AppConstants;
import com.CodingB.Honeypot_AI.Dto.Request.IncomingMessageRequestDto;
import com.CodingB.Honeypot_AI.Entity.IntelligenceRecord;
import com.CodingB.Honeypot_AI.Repository.IntelligenceRecordRepository;
import com.CodingB.Honeypot_AI.Utils.RegexPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;

//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class IntelligenceExtractionService {
//
//    private final IntelligenceRecordRepository intelligenceRepo;
//
//    public void extractAndStoreIntelligence(IncomingMessageRequestDto request) {
//
//        String sessionId = request.getSessionId();
//        String text = request.getMessage().getText();
//
//        log.info("Extracting intelligence from session {}", sessionId);
//
//        try {
//            extractUpiIds(sessionId, text);
//            extractBankAccounts(sessionId, text);
//            extractPhoneNumbers(sessionId, text);
//            extractLinks(sessionId, text);
//            extractKeywords(sessionId, text);
//
//        } catch (Exception e) {
//            log.error("Error during intelligence extraction", e);
//        }
//    }
//
//    private void extractUpiIds(String sessionId, String text) {
//        Matcher matcher = RegexPatternUtil.UPI_PATTERN.matcher(text);
//
//        while (matcher.find()) {
//            saveRecord(sessionId, AppConstants.TYPE_UPI, matcher.group());
//        }
//    }
//
//    private void extractBankAccounts(String sessionId, String text) {
//        Matcher matcher = RegexPatternUtil.BANK_ACCOUNT_PATTERN.matcher(text);
//
//        while (matcher.find()) {
//            saveRecord(sessionId, AppConstants.TYPE_BANK, matcher.group());
//        }
//    }
//
//    private void extractPhoneNumbers(String sessionId, String text) {
//        Matcher matcher = RegexPatternUtil.PHONE_PATTERN.matcher(text);
//
//        while (matcher.find()) {
//            saveRecord(sessionId, AppConstants.TYPE_PHONE, matcher.group());
//        }
//    }
//
//    private void extractLinks(String sessionId, String text) {
//        Matcher matcher = RegexPatternUtil.URL_PATTERN.matcher(text);
//
//        while (matcher.find()) {
//            saveRecord(sessionId, AppConstants.TYPE_LINK, matcher.group());
//        }
//    }
//
//    private void extractKeywords(String sessionId, String text) {
//        for (String keyword : AppConstants.SCAM_KEYWORDS) {
//            if (text.toLowerCase().contains(keyword)) {
//                saveRecord(sessionId, "SUSPICIOUS_KEYWORD", keyword);
//            }
//        }
//    }
//
//    private void saveRecord(String sessionId, String type, String value) {
//        IntelligenceRecord record = new IntelligenceRecord();
//        record.setSessionId(sessionId);
//        record.setType(type);
//        record.setValue(value);
//
//        intelligenceRepo.save(record);
//
//        log.warn("Extracted {} -> {}", type, value);
//    }


@Service
@RequiredArgsConstructor
@Slf4j
public class IntelligenceExtractionService {

    private final IntelligenceRecordRepository repository;

    public void extractFromText(String sessionId, String text) {

        log.info("Extracting intelligence from message for session {}", sessionId);

        if (text == null) return;

        String lower = text.toLowerCase();

        if (lower.contains("upi")) {
            save(sessionId, "SUSPICIOUS_KEYWORD", "upi");
        }
        if (lower.contains("bank")) {
            save(sessionId, "SUSPICIOUS_KEYWORD", "bank");
        }
        if (lower.contains("http")) {
            save(sessionId, "PHISHING_LINK", text);
        }
    }

    private void save(String sessionId, String type, String value) {
        IntelligenceRecord record = new IntelligenceRecord();
        record.setSessionId(sessionId);
        record.setType(type);
        record.setValue(value);

        repository.save(record);
        log.warn("Extracted {} -> {}", type, value);
    }
}

