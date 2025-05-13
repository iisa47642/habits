package ru.habits.spring.habits.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.habits.spring.habits.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeDTO {

    private Long id;

    private User owner;

    private User receiver;

    private String code;

}
