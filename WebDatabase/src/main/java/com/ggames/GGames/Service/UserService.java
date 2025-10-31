package com.ggames.GGames.Service;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.Dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserEntity registerUser(UserDto userDto);

    /**
     * Validates user credentials.
     * Returns true if username exists and password matches, false otherwise.
     * No details are shared to protect against spoofing.
     */
    public boolean validateCredentials(String username, String password);
}
