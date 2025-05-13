package ru.habits.spring.habits.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="progress_frequency")
    private int progressFrequency;

    @Column(name="goal_type")
    private int goalType;

    @Column(name="measurement")
    private String measurement;

    @Column(name="goal")
    private int goal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name="created_at")
    private LocalDate createdAt;


    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private User author;

    @Column(name="is_active")
    private boolean isActive;

    @ManyToMany
    @JoinTable(name="user_habit",
            joinColumns = @JoinColumn(name="habit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    @OneToMany(mappedBy = "habit")
    private List<HabitProgress> habitProgresses;

    public Habit(String name, int progressFrequency, int goalType, String measurement, int goal, LocalDateTime updatedAt, LocalDate createdAt, boolean isActive, User author) {
        this.name = name;
        this.progressFrequency = progressFrequency;
        this.goalType = goalType;
        this.measurement = measurement;
        this.goal = goal;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.author = author;
        this.isActive = isActive;
    }

    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(user);
    }
}
