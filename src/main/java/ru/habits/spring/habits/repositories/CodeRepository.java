package ru.habits.spring.habits.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.habits.spring.habits.models.Code;
import ru.habits.spring.habits.models.User;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Optional<Code> findByOwnerAndReceiver(User owner, User receiver);
}
