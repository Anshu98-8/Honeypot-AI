package com.CodingB.Honeypot_AI.Client;//package com.CodingB.Honeypot_AI.Client;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class OpenAiClient {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${spring.ai.azure.openai.endpoint}")
//    private String endpoint;
//
//    @Value("${spring.ai.azure.openai.api-key}")
//    private String apiKey;
//
//    @Value("${spring.ai.azure.openai.chat.options.deployment-name}")
//    private String deploymentName;
//
//    @Value("${spring.ai.azure.openai.api-version}")
//    private String apiVersion;
//
//    public String generateAgentReply(String prompt) {
//
//        log.info("Calling Azure OpenAI for agent reply...");
//
//        try {
//            String url = endpoint + "/openai/deployments/" + deploymentName
//                    + "/chat/completions?api-version=" + apiVersion;
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("api-key", apiKey);
//
//            // Request body
//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("messages", List.of(
//                    Map.of("role", "system", "content", "You are a normal human responding casually."),
//                    Map.of("role", "user", "content", prompt)
//            ));
//            requestBody.put("max_tokens", 150);
//            requestBody.put("temperature", 0.7);
//
//            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.POST,
//                    entity,
//                    Map.class
//            );
//
//            // Extract AI reply from response
//            List<Map<String, Object>> choices =
//                    (List<Map<String, Object>>) response.getBody().get("choices");
//
//            Map<String, Object> message =
//                    (Map<String, Object>) choices.get(0).get("message");
//
//            String aiReply = (String) message.get("content");
//
//            log.info("Received reply from Azure OpenAI");
//            return aiReply.trim();
//
//        } catch (Exception e) {
//            log.error("Error calling Azure OpenAI", e);
//            return "Sorry, I didn’t understand that. Can you explain again?";
//        }
//    }
//}
//



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiClient {

    private final ChatModel chatModel; // Spring AI injects Azure model automatically

    public String generateAgentReply(String promptText) {
        log.info("Calling Azure OpenAI via Spring AI...");

        try {
            Prompt prompt = new Prompt(promptText);
            String response = chatModel.call(prompt).getResult().getOutput().getText();

            log.info("Received AI response");
            return response.trim();

        } catch (Exception e) {
            log.error("Error calling Azure OpenAI", e);
            return "Sorry, I didn’t understand that. Can you explain again?";
        }
    }
}
