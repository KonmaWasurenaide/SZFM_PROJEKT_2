package com.ggames.GGames.Service.Impl;

import com.ggames.GGames.Data.Entity.ChatMessageEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.ChatMessageRepository;
import com.ggames.GGames.Service.ChatService;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.Dto.ChatMessageDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link ChatService} interfész implementációja, amely a csevegő üzenetek üzleti logikáját kezeli.
 *
 * <p>Felelős az üzenetek adatbázisba mentéséért (biztonsági ellenőrzéssel), valamint a beszélgetés előzményeinek lekéréséért.</p>
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final FriendshipService friendshipService;

    /**
     * Elmenti a bejövő üzenetet az adatbázisba.
     *
     * <p>A mentés előtt a metódus ellenőrzi, hogy a feladó és a címzett szerepel-e a rendszerben,
     * és ami a legfontosabb: **csak akkor menti az üzenetet, ha a felek aktív baráti kapcsolatban állnak**
     * (lásd {@link FriendshipService#areFriends(Long, Long)}).</p>
     *
     * @param messageDto A mentendő üzenetet tartalmazó DTO.
     * @throws RuntimeException Ha a feladó vagy a címzett nem található, vagy ha a felek nem barátok.
     */
    @Transactional
    public void saveMessage(ChatMessageDto messageDto) {
        UserEntity sender = userService.findByUsername(messageDto.getSenderUsername())
                .orElseThrow(() -> new RuntimeException("Feladó nem található: " + messageDto.getSenderUsername()));

        UserEntity receiver = userService.findByUsername(messageDto.getReceiverUsername())
                .orElseThrow(() -> new RuntimeException("Címzett nem található: " + messageDto.getReceiverUsername()));

        if (!friendshipService.areFriends(sender.getId(), receiver.getId())) {
            throw new RuntimeException("Üzenetküldés nem lehetséges: a felek nem barátok.");
        }

        ChatMessageEntity messageEntity = modelMapper.map(messageDto, ChatMessageEntity.class);

        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);
        messageEntity.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(messageEntity);
    }

    /**
     * Lekéri a két felhasználó közötti teljes üzenetelőzményeket időrendben.
     *
     * <p>A metódus az {@link ChatMessageRepository} egyedi lekérdezését használja,
     * majd az entitásokat {@code ChatMessageDto} objektumokká alakítja.</p>
     *
     * @param userId1 Az első felhasználó azonosítója.
     * @param userId2 A második felhasználó azonosítója.
     * @return {@code ChatMessageDto} objektumok listája, amely tartalmazza a beszélgetés történetét.
     */
    public List<ChatMessageDto> getConversationHistory(Long userId1, Long userId2) {
        return chatMessageRepository.findConversationHistory(userId1, userId2).stream()
                .map(entity -> {
                    ChatMessageDto dto = modelMapper.map(entity, ChatMessageDto.class);

                    // A model mapper nem tudja automatikusan leképezni az entitásból a felhasználóneveket
                    dto.setSenderUsername(entity.getSender().getUsername());
                    dto.setReceiverUsername(entity.getReceiver().getUsername());

                    return dto;
                })
                .collect(Collectors.toList());
    }
}