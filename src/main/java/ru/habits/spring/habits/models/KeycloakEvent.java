package ru.habits.spring.habits.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakEvent {
    private String type;
    private Map<String, String> details;
}
