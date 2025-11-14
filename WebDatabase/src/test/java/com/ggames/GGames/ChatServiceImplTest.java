package com.ggames.GGames;

import com.ggames.GGames.Data.Entity.ChatMessageEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.ChatMessageRepository;
import com.ggames.GGames.Service.Dto.ChatMessageDto;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.Impl.ChatServiceImpl;
import com.ggames.GGames.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceImplTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;
    @Mock
    private UserService userService;
    @Mock
    private FriendshipService friendshipService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ChatServiceImpl chatService;

    private ChatMessageDto messageDto;
    private UserEntity sender;
    private UserEntity receiver;
    private ChatMessageEntity messageEntity;

    @BeforeEach
    void setUp() {
        messageDto = new ChatMessageDto();
        messageDto.setSenderUsername("alice");
        messageDto.setReceiverUsername("bob");
        messageDto.setContent("Szia Bob!");

        sender = new UserEntity();
        sender.setId(1L);
        sender.setUsername("alice");

        receiver = new UserEntity();
        receiver.setId(2L);
        receiver.setUsername("bob");

        messageEntity = new ChatMessageEntity();
        messageEntity.setId(100L);
        messageEntity.setContent("Szia Bob!");
    }

    @Test
    void testSaveMessage_Success() {
        when(userService.findByUsername("alice")).thenReturn(Optional.of(sender));
        when(userService.findByUsername("bob")).thenReturn(Optional.of(receiver));
        when(friendshipService.areFriends(1L, 2L)).thenReturn(true);
        when(modelMapper.map(any(ChatMessageDto.class), eq(ChatMessageEntity.class))).thenReturn(messageEntity);

        chatService.saveMessage(messageDto);

        verify(userService, times(1)).findByUsername("alice");
        verify(userService, times(1)).findByUsername("bob");
        verify(friendshipService, times(1)).areFriends(1L, 2L);

        verify(chatMessageRepository, times(1)).save(argThat(entity ->
                entity.getSender().equals(sender) &&
                        entity.getReceiver().equals(receiver) &&
                        entity.getTimestamp() != null
        ));
    }

    @Test
    void testSaveMessage_SenderNotFound_ThrowsException() {
        when(userService.findByUsername("alice")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatService.saveMessage(messageDto);
        });

        assertEquals("Feladó nem található: alice", exception.getMessage());
        verify(chatMessageRepository, never()).save(any());
        verify(friendshipService, never()).areFriends(any(), any());
    }

    @Test
    void testSaveMessage_ReceiverNotFound_ThrowsException() {
        when(userService.findByUsername("alice")).thenReturn(Optional.of(sender));
        when(userService.findByUsername("bob")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatService.saveMessage(messageDto);
        });

        assertEquals("Címzett nem található: bob", exception.getMessage());
        verify(chatMessageRepository, never()).save(any());
        verify(friendshipService, never()).areFriends(any(), any());
    }

    @Test
    void testSaveMessage_NotFriends_ThrowsException() {
        // Mock viselkedések beállítása: Feladók OK, de NEM barátok
        when(userService.findByUsername("alice")).thenReturn(Optional.of(sender));
        when(userService.findByUsername("bob")).thenReturn(Optional.of(receiver));
        when(friendshipService.areFriends(1L, 2L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatService.saveMessage(messageDto);
        });

        assertEquals("Üzenetküldés nem lehetséges: a felek nem barátok.", exception.getMessage());
        verify(chatMessageRepository, never()).save(any());
    }

    @Test
    void testGetConversationHistory_Success() {
        Long userIdA = 10L;
        Long userIdB = 20L;

        ChatMessageEntity entity1 = createMessageEntity(1L, userIdA, userIdB, "Elso üzenet", LocalDateTime.now());
        ChatMessageEntity entity2 = createMessageEntity(2L, userIdB, userIdA, "Masodik üzenet", LocalDateTime.now().plusSeconds(1));
        List<ChatMessageEntity> entities = List.of(entity1, entity2);

        ChatMessageDto dto1 = createMessageDto("userA", "userB", "Elso üzenet");
        ChatMessageDto dto2 = createMessageDto("userB", "userA", "Masodik üzenet");

        when(chatMessageRepository.findConversationHistory(userIdA, userIdB)).thenReturn(entities);
        when(modelMapper.map(entity1, ChatMessageDto.class)).thenReturn(dto1);
        when(modelMapper.map(entity2, ChatMessageDto.class)).thenReturn(dto2);

        List<ChatMessageDto> result = chatService.getConversationHistory(userIdA, userIdB);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("userA", result.get(0).getSenderUsername());
        assertEquals("userB", result.get(1).getSenderUsername());

        verify(chatMessageRepository, times(1)).findConversationHistory(userIdA, userIdB);
        verify(modelMapper, times(2)).map(any(ChatMessageEntity.class), eq(ChatMessageDto.class));
    }

    @Test
    void testGetConversationHistory_Empty() {
        Long userIdA = 10L;
        Long userIdB = 20L;

        when(chatMessageRepository.findConversationHistory(userIdA, userIdB)).thenReturn(List.of());

        List<ChatMessageDto> result = chatService.getConversationHistory(userIdA, userIdB);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(chatMessageRepository, times(1)).findConversationHistory(userIdA, userIdB);
        verify(modelMapper, never()).map(any(), any());
    }

    private ChatMessageEntity createMessageEntity(Long id, Long senderId, Long receiverId, String content, LocalDateTime timestamp) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setId(id);
        entity.setContent(content);
        entity.setTimestamp(timestamp);

        UserEntity sender = new UserEntity();
        sender.setId(senderId);
        sender.setUsername(senderId == 10L ? "userA" : "userB");

        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);
        receiver.setUsername(receiverId == 20L ? "userB" : "userA");

        entity.setSender(sender);
        entity.setReceiver(receiver);
        return entity;
    }

    private ChatMessageDto createMessageDto(String sender, String receiver, String content) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setSenderUsername(sender);
        dto.setReceiverUsername(receiver);
        dto.setContent(content);
        return dto;
    }
}