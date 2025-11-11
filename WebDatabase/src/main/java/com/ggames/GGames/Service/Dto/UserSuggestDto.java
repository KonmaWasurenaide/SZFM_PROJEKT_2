package com.ggames.GGames.Service.Dto;

import com.ggames.GGames.Data.Entity.UserEntity;
import lombok.Value;

@Value
public class UserSuggestDto {
    private Long id;
    private String username;

    public static UserSuggestDto fromEntity(UserEntity user) {
        return new UserSuggestDto(user.getId(), user.getUsername());
    }
}