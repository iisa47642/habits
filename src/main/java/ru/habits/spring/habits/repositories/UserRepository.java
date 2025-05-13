package ru.habits.spring.habits.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.habits.spring.habits.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKeycloakSub(String sub);
}
