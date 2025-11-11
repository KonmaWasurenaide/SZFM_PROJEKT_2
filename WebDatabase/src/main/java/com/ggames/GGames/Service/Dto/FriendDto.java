package com.ggames.GGames.Service.Dto;

import com.ggames.GGames.Data.Entity.UserEntity;
import lombok.Value;

@Value
public class FriendDto {
    private Long id;
    private String username;

    public static FriendDto fromEntity(UserEntity user) {
        return new FriendDto(user.getId(), user.getUsername());
    }
}