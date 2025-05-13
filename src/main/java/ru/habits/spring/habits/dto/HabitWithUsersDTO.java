package ru.habits.spring.habits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.habits.spring.habits.models.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HabitWithUsersDTO {
    private String name;

    private int progressFrequency;

    private int goalType;

    private String measurement;

    private int goal;

    private Long authorId;

    private List<User> users;
}
