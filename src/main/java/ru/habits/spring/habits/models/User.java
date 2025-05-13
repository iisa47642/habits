package ru.habits.spring.habits.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="avatar_url")
    private String avatarUrl;

    @Column(name="keycloak_sub")
    private String keycloakSub;

    @ManyToMany(mappedBy = "users")
    private List<Habit> habits;

    @ManyToMany
    @JoinTable(name="user_friend",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="friend_id", referencedColumnName = "id"))
    private List<User> friends;


    @OneToMany(mappedBy = "user")
    private List<HabitProgress> habitProgresses;


    public void addHabit(Habit habit) {
        if (this.habits == null) {
            this.habits = new ArrayList<>();
        }
        this.habits.add(habit);
    }

    public void addFriend(User user) {
        if (this.friends == null) {
            this.friends = new ArrayList<>();
        }
        this.friends.add(user);
    }

    public User(String name, String keycloakSub) {
        this.name = name;
        this.keycloakSub = keycloakSub;
    }
}
