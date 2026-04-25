package com.lms.library.ai_chatbot;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lms.library.ai_chatbot.dto.ChatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai-chat")
@RequiredArgsConstructor
public class ChatController {

    private final GeminiService geminiService;

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody ChatRequest request) {
        String aiResponse = geminiService.chatWithGemini(request.getMessage());
        // Trả về JSON: { "reply": "Nội dung trả lời từ AI" }
        return ResponseEntity.ok(Map.of("reply", aiResponse));
    }
}