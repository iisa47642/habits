package ru.habits.spring.habits.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.habits.spring.habits.dto.HabitProgressDTO;
import ru.habits.spring.habits.models.HabitProgress;

@Controller
public class ProgressController {


    @MessageMapping("/habit/{id}/progress")
    @SendTo("/topic/habit/{id}/progress")
    public HabitProgressDTO greeting(@DestinationVariable String habitId,
                                     HabitProgressDTO habitProgressDTO) throws Exception {
        return habitProgressDTO;
    }

}
