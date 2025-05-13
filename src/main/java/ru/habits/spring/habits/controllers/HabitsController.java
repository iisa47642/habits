package ru.habits.spring.habits.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.habits.spring.habits.dto.AttachmentDTO;
import ru.habits.spring.habits.dto.HabitDTO;
import ru.habits.spring.habits.dto.HabitProgressDTO;
import ru.habits.spring.habits.dto.HabitWithUsersDTO;
import ru.habits.spring.habits.models.*;
import ru.habits.spring.habits.services.HabitService;
import ru.habits.spring.habits.services.UserService;
import ru.habits.spring.habits.utils.HabitErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/habits")
public class HabitsController {
    private final HabitService habitService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public HabitsController(HabitService habitService, ModelMapper modelMapper, UserService userService) {
        this.habitService = habitService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<HttpStatus> bulkHabits(@RequestBody List<HabitDTO> habits) {
        habitService.createHabits(habits.stream().map(this::convertToHabit).collect(Collectors.toList()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createHabit(@RequestBody HabitDTO habitDTO) {
        habitService.createHabit(convertToHabit(habitDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping
//    @PreAuthorize("hasRole('admin')")
    public List<HabitDTO> getHabits() {
        return habitService.getHabits().stream().map(this::convertToHabitDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HabitWithUsersDTO getHabitById(@PathVariable Long id) {
        return convertToHabitWithUsersDTO(habitService.getHabitWithUsersById(id));
    }

    @GetMapping("/{id}/progress")
    public List<HabitProgressDTO> getHabitProgressesById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        String sub = (String) jwt.getClaim("sub");
        User user = userService.getUserBySub(sub);
        return habitService.getHabitProgresses(id, user).
                stream().map(this::convertToHabitProgressDTO).collect(Collectors.toList());
    }

    @PostMapping(
            value = "/{id}/progress",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createHabitProgress(
            @PathVariable Long id,
            @RequestPart HabitProgressWithFilesDTO habitProgressWithFilesDTO,
            @AuthenticationPrincipal Jwt jwt) {
        String sub = (String) jwt.getClaim("sub");
        User user = userService.getUserBySub(sub);
        habitService.createHabitProgress(habitProgressWithFilesDTO, user, id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @GetMapping("/{id}/progress/{user_id}")
    public List<HabitProgressDTO> getHabitProgressesById(@PathVariable Long id, @PathVariable("user_id") Long userId) {
        return habitService.getHabitProgresses(id, userId).
                stream().map(this::convertToHabitProgressDTO).collect(Collectors.toList());
    }


    @PostMapping("/{id}/invite/{user_id}")
    public ResponseEntity<HttpStatus> inviteUserToHabit(@PathVariable Long id, @PathVariable("user_id") Long userId) {
        habitService.inviteUserToHabit(id, userId);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    private Habit convertToHabit(HabitDTO habitDTO) {
        return modelMapper.map(habitDTO, Habit.class);
    }

    private HabitDTO convertToHabitDTO(Habit habit) {
        return modelMapper.map(habit, HabitDTO.class);
    }

    private HabitWithUsersDTO convertToHabitWithUsersDTO(Habit habit) {
        return modelMapper.map(habit, HabitWithUsersDTO.class);
    }

    private HabitProgress convertToHabitProgress(HabitProgressDTO habitProgressDTO) {
        return modelMapper.map(habitProgressDTO, HabitProgress.class);
    }

    private HabitProgressDTO convertToHabitProgressDTO(HabitProgress habitProgress) {
        HabitProgressDTO habitProgressDTO  = modelMapper.map(habitProgress, HabitProgressDTO.class);

        List<AttachmentDTO>  attachmentDTOS = habitProgressDTO.getAttachments().
                stream().map(x -> modelMapper.
                        map(x, AttachmentDTO.class)).collect(Collectors.toList());

        habitProgressDTO.setAttachments(attachmentDTOS);

        return habitProgressDTO;
    }

    @ExceptionHandler
    public ResponseEntity<HabitErrorResponse> handleException(Exception ex) {
        HabitErrorResponse response = new HabitErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
