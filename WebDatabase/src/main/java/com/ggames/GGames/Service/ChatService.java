package com.ggames.GGames.Service;

import com.ggames.GGames.Service.Dto.ChatMessageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

    /**
     * Elmenti a bejövő üzenetet az adatbázisba.
     * (Ezt kell meghívni a ChatController.processMessage metódusban!)
     */
    public void saveMessage(ChatMessageDto messageDto);

    /**
     * Lekéri a két felhasználó közötti üzenetelőzményeket.
     * (Ezt hívja meg a ChatHistoryController.)
     */
    public List<ChatMessageDto> getConversationHistory(Long userId1, Long userId2);
}