package com.lms.library.ai_chatbot;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.library.entity.DauSach;
import com.lms.library.repository.DauSachRepository;

@Service
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final DauSachRepository dauSachRepository;
    private String systemInstruction = "You are a virtual assistant.";

    public GeminiService(DauSachRepository dauSachRepository) {
        this.dauSachRepository = dauSachRepository;
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

        // 3. Gửi cái enrichedMessage này cho Gemini (Giữ nguyên code cũ của bạn)
        Map<String, Object> requestBody = Map.of(
                "system_instruction", Map.of(
                        "parts", new Object[] { Map.of("text", systemInstruction) }),
                "contents", new Object[] {
                        Map.of("parts", new Object[] { Map.of("text", userMessage) }) // Đổi biến ở đây
                });
        try {
       String responseStr = restClient.post()
                    .uri("/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            // 2. Dùng ObjectMapper của Lin để tự parse chuỗi String thành JsonNode
            com.fasterxml.jackson.databind.JsonNode responseNode = objectMapper.readTree(responseStr);

            // 3. Bóc tách lấy đúng câu trả lời
            if (responseNode != null && responseNode.has("candidates")) {
                return responseNode.path("candidates").get(0)
                        .path("content")
                        .path("parts").get(0)
                        .path("text").asText();
            }
            return "Xin lỗi, tôi không thể xử lý câu trả lời lúc này.";
            
        } catch (Exception e) {
            return "Lỗi kết nối đến BiblioBot: " + e.getMessage();
        }
    }
}