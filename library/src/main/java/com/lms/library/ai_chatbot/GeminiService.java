package com.lms.library.ai_chatbot;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private String systemInstruction = "You are a virtual assistant.";

    public GeminiService() {
        this.restClient = RestClient.create("https://generativelanguage.googleapis.com");
        this.objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        ;
        loadPromptConfig(); // Auto-load JSON file on Spring Boot startup
    }

    private void loadPromptConfig() {
        try {
            ClassPathResource resource = new ClassPathResource("project_prompt.json");
            byte[] byteData = resource.getInputStream().readAllBytes();
            this.systemInstruction = new String(byteData, StandardCharsets.UTF_8);
            System.out.println("Successfully loaded full context for BiblioBot!");
        } catch (Exception e) {
            System.out.println("Error reading project_prompt.json: " + e.getMessage());
        }
    }

    public String chatWithGemini(String userMessage) {
        Map<String, Object> requestBody = Map.of(
                "system_instruction", Map.of(
                        "parts", new Object[] { Map.of("text", systemInstruction) }),
                "contents", new Object[] {
                        Map.of("parts", new Object[] { Map.of("text", userMessage) })
                });

        try {
            return restClient.post()
                    .uri("/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            return "Error connecting to AI: " + e.getMessage();
        }
    }
}