package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.ChatService;
import com.ggames.GGames.Service.Dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Kezeli a WebSocket alapú csevegéshez kapcsolódó STOMP üzeneteket.
 *
 * <p>Ez az osztály felelős a privát üzenetek fogadásáért, elmentéséért és továbbításáért a küldő és a címzett felhasználóknak.</p>
 *
 * @author A kód szerzője
 * @since 1.0
 */
@Controller
public class ChatController {

    /**
     * Segédobjektum STOMP üzenetek küldésére a csatlakoztatott klienseknek.
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Regisztrálja és kezeli a jelenleg csatlakoztatott STOMP felhasználókat.
     */
    private final SimpUserRegistry simpUserRegistry;

    /**
     * Szolgáltatás a csevegő üzenetek üzleti logikájának kezelésére (mentés az adatbázisba).
     */
    private final ChatService chatService;

    /**
     * Konstruktor a {@code ChatController} inicializálásához.
     *
     * @param messagingTemplate A {@code SimpMessagingTemplate} injektált példánya, üzenetek küldésére.
     * @param simpUserRegistry A {@code SimpUserRegistry} injektált példánya, csatlakoztatott felhasználók kezelésére.
     * @param chatService A {@code ChatService} injektált példánya, az üzenetek adatbázisba mentéséhez.
     */
    public ChatController(SimpMessagingTemplate messagingTemplate,
                          SimpUserRegistry simpUserRegistry,
                          ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.chatService = chatService;
    }

    /**
     * Fogadja a kliensektől érkező csevegő üzeneteket a {@code "/app/chat"} célon.
     *
     * <p>Az üzenet feldolgozása során beállítja a küldő nevét (az autentikált felhasználót), elmenti az üzenetet
     * a {@link ChatService} segítségével, majd valós időben továbbítja az üzenetet mind a küldő, mind a címzett
     * számára a privát üzenet sorba (pl. {@code /user/{username}/queue/messages}).</p>
     *
     * @param chatMessage Az üzenetet tartalmazó adatátviteli objektum (DTO).
     * @param principal A bejelentkezett felhasználó azonosítója (a küldő) a Spring Security-ből.
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessage, Principal principal) {
        String sender = principal.getName();
        String receiver = chatMessage.getReceiverUsername();
        chatMessage.setSenderUsername(sender);
        chatService.saveMessage(chatMessage);
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