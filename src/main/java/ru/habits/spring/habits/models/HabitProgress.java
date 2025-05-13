package ru.habits.spring.habits.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="habit_progress")
public class HabitProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="habit_id", referencedColumnName = "id")
    private Habit habit;

    @Column(name="progress")
    private int progress;

    @Column(name="sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sentAt;

    @OneToMany(mappedBy = "habitProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    public HabitProgress(User user, Habit habit, int progress, LocalDateTime sentAt) {
        this.user = user;
        this.habit = habit;
        this.progress = progress;
        this.sentAt = sentAt;
    }
}
