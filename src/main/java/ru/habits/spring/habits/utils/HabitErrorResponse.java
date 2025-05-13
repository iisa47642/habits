package ru.habits.spring.habits.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.ErrorResponse;

@Getter
@Setter
@AllArgsConstructor
public class HabitErrorResponse {
    private String message;
    private long timestamp;
}
