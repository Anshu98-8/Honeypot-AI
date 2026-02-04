package com.CodingB.Honeypot_AI.Utils;


import java.util.regex.Pattern;

public class RegexPatternUtil {

    // Example: scammer@upi, name@okaxis
    public static final Pattern UPI_PATTERN =
            Pattern.compile("\\b[\\w.-]+@[a-zA-Z]+\\b");

    // Simple bank account number pattern (10–18 digits)
    public static final Pattern BANK_ACCOUNT_PATTERN =
            Pattern.compile("\\b\\d{10,18}\\b");

    // Indian phone numbers (+91XXXXXXXXXX or 10 digits)
    public static final Pattern PHONE_PATTERN =
            Pattern.compile("(\\+91[- ]?)?[6-9]\\d{9}");

    // URLs (http / https links)
    public static final Pattern URL_PATTERN =
            Pattern.compile("https?://[\\w./?=&%-]+", Pattern.CASE_INSENSITIVE);

    private RegexPatternUtil() {
        // Utility class
    }
}

