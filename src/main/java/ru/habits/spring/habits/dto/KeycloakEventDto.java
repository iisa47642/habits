package ru.habits.spring.habits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakEventDto {
    private String type;
    private String realmId;
    private String clientId;
    private String userId;
    private long time;
    private Map<String, String> details;
}

