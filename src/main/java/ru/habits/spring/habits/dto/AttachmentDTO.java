package ru.habits.spring.habits.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.habits.spring.habits.models.HabitProgress;

@Getter
@Setter
@AllArgsConstructor
public class AttachmentDTO {
    private int type;

    private String url;
}
