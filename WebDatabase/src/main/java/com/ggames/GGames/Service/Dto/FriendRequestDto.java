package com.ggames.GGames.Service.Dto;

import com.ggames.GGames.Data.Entity.FriendshipEntity;
import lombok.Value;

@Value
public class FriendRequestDto {
    private Long requestId;
    private Long senderId;
    private String senderUsername;
    private String status;


    public static FriendRequestDto fromEntity(FriendshipEntity friendship) {
        return new FriendRequestDto(
                friendship.getId(),
                friendship.getSender().getId(),
                friendship.getSender().getUsername(),
                friendship.getStatus().name()
        );
    }
}