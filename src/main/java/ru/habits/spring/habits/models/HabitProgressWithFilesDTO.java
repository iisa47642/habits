package ru.habits.spring.habits.models;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class HabitProgressWithFilesDTO {
    private User user;

    private Habit habit;

    private int progress;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sentAt;

    private List<MultipartFile> attachmentFiles;
}
