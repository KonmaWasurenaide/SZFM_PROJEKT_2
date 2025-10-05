package com.ggames.GGames.Service;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity registerUser(String username, String email, String password) {
        // validáció
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }
        if (password.length() < 6) {
            throw new RuntimeException("Password too short!");
        }

        // jelszó hash-elés a jbcrypt-tel
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserEntity user = new UserEntity(username, email, hashedPassword);
        return userRepository.save(user);
    }
}