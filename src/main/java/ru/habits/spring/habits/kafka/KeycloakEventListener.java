package ru.habits.spring.habits.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.habits.spring.habits.dto.KeycloakEventDto;
import ru.habits.spring.habits.services.UserService;

@Component
public class KeycloakEventListener {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public KeycloakEventListener(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "keycloak-events", groupId = "keycloak-user-events-consumer")
    public void listen(String message) {
        try {
            // Преобразуем JSON-сообщение из Kafka в объект события
            KeycloakEventDto event = objectMapper.readValue(message, KeycloakEventDto.class);


            if ("REGISTER".equals(event.getType())) {
                userService.createUser(event);
            }

        } catch (Exception e) {
            System.err.println("Ошибка обработки события из Kafka: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
