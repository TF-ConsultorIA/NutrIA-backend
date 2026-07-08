package com.nutria.nutria_api.chatbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {
    private final ChatClient foodChatClient;
    private final ChatModel chatModel;

    public String recommendation(String message) {
        return foodChatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
