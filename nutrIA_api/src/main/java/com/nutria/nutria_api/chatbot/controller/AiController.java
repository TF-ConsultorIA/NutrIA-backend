package com.nutria.nutria_api.chatbot.controller;

import com.nutria.nutria_api.chatbot.dto.ChatResponse;
import com.nutria.nutria_api.chatbot.service.impl.AiService;
import com.nutria.nutria_api.chatbot.service.impl.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Tag(name = "AI", description = "API para el chatbot")
public class AiController {
    private final AiService aiService;
    private final SupportService supportService;

    @Operation(description = "User: Realiza una consulta sobre una comida")
    @PostMapping("/recommendation")
    public ResponseEntity<ChatResponse> recommendation(String message) {
        return ResponseEntity.ok(new ChatResponse(aiService.recommendation(message)));
    }

    @Operation(description = "User: Realiza una consulta de soporte respondida en base a los documentos (RAG)")
    @PostMapping("support/ask")
    public ResponseEntity<ChatResponse> ask(String message) {
        return ResponseEntity.ok(new ChatResponse(supportService.ask(message)));
    }

    @Operation(description = "Admin: Ingesta los documentos PDF al vector store")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("support/ingest")
    public Map<String, Object> supportIngest() throws IOException {
        return Map.of("chunksIngested",supportService.ingest());
    }
}
