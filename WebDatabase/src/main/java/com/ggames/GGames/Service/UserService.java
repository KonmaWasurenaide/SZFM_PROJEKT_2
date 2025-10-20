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

    public UserEntity registerUser(String username, String password) {
        // validáció
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        if (password.length() < 6) {
            throw new RuntimeException("Password too short!");
        }

        // jelszó hash-elés a jbcrypt-tel
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserEntity user = new UserEntity(username, hashedPassword);
        return userRepository.save(user);
    }

   
    public boolean validateCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> org.mindrot.jbcrypt.BCrypt.checkpw(password, user.getPassword()))
                .orElse(false);
    }
}