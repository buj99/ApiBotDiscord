package com.example.ApiBotDiscord.Repositories;

import com.example.ApiBotDiscord.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByIdDiscord(String idDiscord);
    Long deleteUserByIdDiscord(String idDiscord);
}
