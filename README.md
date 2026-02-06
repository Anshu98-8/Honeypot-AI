# 🛡️ Honeypot AI – Intelligent Scam Detection & Engagement System

An AI-powered scam detection and honeypot backend system designed for national-level hackathon deployment.

This system:
- Detects scam messages in real-time
- Engages scammers using AI-driven human-like responses
- Extracts intelligence from conversations
- Stores full conversation history
- Deploys securely on Azure Cloud

Built using:
- Java (Spring Boot)
- Azure OpenAI
- PostgreSQL (Azure Flexible Server)
- Azure App Service
- REST API Architecture

---

# 🚀 Problem Statement

Online scams are evolving rapidly. Traditional rule-based systems fail to:
- Detect new scam patterns
- Adapt to unknown scam types
- Extract useful intelligence
- Engage scammers for behavioral analysis

This project builds a smart AI honeypot that:
- Talks like a real human
- Engages scammers in long conversations
- Avoids sharing sensitive information
- Extracts useful scam intelligence
- Logs all interactions

---

# 🧠 How It Works

1️⃣ Incoming Message  
Scammer sends a message (SMS/Chat/API).

2️⃣ Scam Detection  
Fast keyword-based detection identifies suspicious content.

3️⃣ AI Engagement  
If suspicious:
- AI generates a human-like reply
- Engages scammer emotionally
- Avoids sharing real data
- Keeps conversation going

4️⃣ Intelligence Extraction  
System extracts:
- Suspicious keywords
- Bank references
- OTP requests
- Urgency patterns

5️⃣ Memory & Reporting  
- Saves full conversation
- Tracks session statistics
- Prepares final report if required

---

# 🏗️ Architecture Overview

Controller  
   ↓  
MessageProcessingService  
   ↓  
 ├── ScamDetectionService  
 ├── AgentConversationService  
 │       ↓  
 │    Azure OpenAI  
 ├── IntelligenceExtractionService  
 ├── ConversationMemoryService  
 └── GuviReportingService  

---

# 🛠️ Tech Stack

Backend: Spring Boot  
AI Model: Azure OpenAI  
Database: PostgreSQL (Azure Flexible Server)  
Cloud Hosting: Azure App Service  
Security: API Key Validation  
Monitoring: Cron-job health check  

---

# 📂 Project Structure

src/main/java/com/CodingB/Honeypot_AI

├── Controller  
├── Service  
├── Repository  
├── Entity  
├── Dto  
├── Client (Azure OpenAI)  
├── Config  

---

# 🔐 API Security

All endpoints require:

x-api-key: YOUR_SECRET_KEY

This prevents unauthorized access.

---

# 📡 API Endpoint

POST /api/honeypot/message

Request Example:

{
  "sessionId": "test123",
  "sender": "scammer",
  "text": "URGENT: Your bank account will be blocked. Share OTP now.",
  "timestamp": 1707120000000,
  "channel": "SMS",
  "language": "en",
  "locale": "IN"
}

Response Example:

{
  "status": "success",
  "reply": "Oh no, that sounds serious. Why would my account be compromised?"
}

---

# ☁️ Azure Deployment Guide

Step 1 – Create PostgreSQL Database
- Azure Portal → Azure Database for PostgreSQL Flexible Server
- Select Dev/Test (free tier eligible)
- Allow Azure services access
- Note host, username, password, port

Step 2 – Configure Application Properties Using Environment Variables

SPRING_DATASOURCE_URL  
SPRING_DATASOURCE_USERNAME  
SPRING_DATASOURCE_PASSWORD  
AZURE_OPENAI_ENDPOINT  
AZURE_OPENAI_KEY  
API_SECRET_KEY  

Step 3 – Create Azure App Service
- Runtime: Java 17
- OS: Linux
- Pricing Tier: Basic (recommended for Always On)
- Enable HTTPS

Step 4 – Deploy Backend
Option 1:
Upload JAR via Azure Deployment Center

Option 2:
Connect GitHub repository

Step 5 – Configure Environment Variables
App Service → Configuration → Application Settings

Add:
- Database credentials
- Azure OpenAI keys
- API key

Restart application after saving.

---

# 🧪 Local Setup

1️⃣ Clone Project

git clone <https://github.com/Anshu98-8/Honeypot-AI.git>

2️⃣ Configure application.properties

spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
azure.openai.endpoint=
azure.openai.key=

3️⃣ Run

mvn spring-boot:run

---

# 🔮 Future Improvements

- Vector Database (RAG-based learning)
- Dynamic scam pattern training
- ML-based anomaly detection
- Real-time scam dashboard
- Analytics engine
- Multi-language support
- Auto-scam classification model

---

# 🏆 Hackathon Submission

Built and deployed for:

AI Impact Summit – National Level Hackathon

Fully deployed backend with:
- Cloud database
- AI integration
- Secure API
- Monitoring system

---

# 👨‍💻 Author

Anshu98-8 
Backend Developer | AI Enthusiast  
Focused on building intelligent systems that solve real-world problems.

If you found this project interesting, consider giving it a ⭐ on GitHub.
