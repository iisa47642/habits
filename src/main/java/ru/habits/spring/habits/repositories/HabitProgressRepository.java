package ru.habits.spring.habits.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.habits.spring.habits.models.Habit;
import ru.habits.spring.habits.models.HabitProgress;
import ru.habits.spring.habits.models.User;

import java.util.List;
import java.util.Optional;

public interface HabitProgressRepository extends JpaRepository<HabitProgress, Long> {

    @Query("SELECT hp FROM HabitProgress hp " +
            "LEFT JOIN FETCH hp.attachments " +  // Загружаем attachments сразу
            "WHERE hp.user = :user AND hp.habit = :habit")
    List<HabitProgress> findByUserAndHabitWithAttachments(
            @Param("user") User user,
            @Param("habit") Habit habit);
}
