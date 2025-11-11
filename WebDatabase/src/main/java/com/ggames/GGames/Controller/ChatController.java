package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.Dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public ChatController(SimpMessagingTemplate messagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessage, Principal principal) {
        String sender = principal.getName();
        String receiver = chatMessage.getReceiverUsername();


        messagingTemplate.convertAndSendToUser(
                receiver,
                "/queue/messages",
                chatMessage
        );

        messagingTemplate.convertAndSendToUser(
                sender,
                "/queue/messages",
                chatMessage
        );
    }
}
