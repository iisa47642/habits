package ru.habits.spring.habits.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.habits.spring.habits.models.Habit;
import ru.habits.spring.habits.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HabitProgressDTO {

    private User user;

    private Habit habit;

    private int progress;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sentAt;

    private List<AttachmentDTO> attachments;
}
