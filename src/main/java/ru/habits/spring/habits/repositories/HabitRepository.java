package ru.habits.spring.habits.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.habits.spring.habits.models.Habit;

public interface HabitRepository extends JpaRepository<Habit, Long> {
}
