package com.ggames.GGames.Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Adatátviteli objektum (DTO) a csevegő üzenetek adatainak szállítására az alkalmazás rétegei között.
 *
 * <p>Ezt az objektumot használják a front-end és a back-end közötti kommunikációhoz,
 * különösen STOMP (WebSocket) üzenetek esetén, valamint az üzenet előzmények lekérésekor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    /**
     * Az üzenetet küldő felhasználó bejelentkezési neve.
     */
    private String senderUsername;
    /**
     * Az üzenetet fogadó felhasználó bejelentkezési neve.
     */
    private String receiverUsername;
    /**
     * Az üzenet szöveges tartalma.
     */
    private String content;
    /**
     * Az üzenet elküldésének időbélyege, általában szöveges formátumban.
     */
    private String timestamp;
}