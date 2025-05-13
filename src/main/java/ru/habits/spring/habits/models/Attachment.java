package ru.habits.spring.habits.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="habit_progress_id")
    private HabitProgress habitProgress;

    @Column(name="type")
    private int type;

    @Column(name="url")
    private String url;

    public Attachment(int type, String url) {
        this.type = type;
        this.url = url;
    }
}
