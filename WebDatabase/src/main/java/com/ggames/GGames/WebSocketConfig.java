package com.ggames.GGames;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Konfigurációs osztály a Spring WebSocket és STOMP üzenetközvetítő beállításához.
 *
 * <p>Engedélyezi a WebSocket üzenetközvetítést, meghatározza a STOMP végpontot,
 * és konfigurálja az üzenetközvetítő előtagokat (prefixeket).</p>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Regisztrálja a STOMP végpontot, amelyre a kliensek csatlakozhatnak.
     *
     * <p>A {@code /ws} végpontot konfigurálja, és engedélyezi a SockJS fallback támogatást
     * olyan böngészők számára, amelyek nem támogatják natívan a WebSocketet.
     * Engedélyezi az összes forrásból ({@code "*"}) érkező kapcsolatot.</p>
     *
     * @param registry A STOMP végpont regisztrátor.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * Konfigurálja az üzenetközvetítőket (message brokers).
     *
     * <p>Egyszerű üzenetközvetítőt engedélyez a {@code /topic} (publikus üzenetek) és
     * a {@code /queue} (privát üzenetek) célokra.
     * Beállítja az alkalmazás által kezelt célok előtagját (pl. {@code /app/chat}),
     * valamint a felhasználó-specifikus privát üzenetek céljának előtagját ({@code /user}).</p>
     *
     * @param config Az üzenetközvetítő beállításainak regisztrátora.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }
}