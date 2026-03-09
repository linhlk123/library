package com.lms.library.ai_chatbot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.ai_chatbot.dto.ChatRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai-chat")
@RequiredArgsConstructor
public class ChatController {

    private final GeminiService geminiService;

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {
        return geminiService.chatWithGemini(request.getMessage());
    }
}