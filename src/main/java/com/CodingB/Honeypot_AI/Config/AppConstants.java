package com.CodingB.Honeypot_AI.Config;

public class AppConstants {

    // Scam trigger keywords
    public static final String[] SCAM_KEYWORDS = {"verify", "urgent", "account blocked", "suspend", "upi", "bank", "otp"};

    // Intelligence types
    public static final String TYPE_UPI = "UPI_ID";
    public static final String TYPE_BANK = "BANK_ACCOUNT";
    public static final String TYPE_LINK = "PHISHING_LINK";
    public static final String TYPE_PHONE = "PHONE_NUMBER";

    private AppConstants() {
        // Prevent object creation
    }
}

