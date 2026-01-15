package com.example.demo.presentation.controller.websocket

import com.example.demo.presentation.dto.websockets.DummySocketMessage
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.time.Instant

@Controller
class DummyWebSocketController {

    private val log = LoggerFactory.getLogger(DummyWebSocketController::class.java)

    @MessageMapping("/ping")      // client sends to /app/ping
    @SendTo("/topic/pong")        // client listens to /topic/pong
    fun sendDummy(payload: DummySocketMessage): DummySocketMessage {

        log.info("🔥 /app/ping RECEIVED -> {}", payload)

        val response = payload.copy(
            timestamp = Instant.now()
        )

        log.info("📤 Sending message to /topic/pong -> {}", response)
        return response
    }
}

