package ru.habits.spring.habits.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HabitDTO {
    private String name;

    private int progressFrequency;

    private int goalType;

    private String measurement;

    private int goal;

    private Long authorId;
}
