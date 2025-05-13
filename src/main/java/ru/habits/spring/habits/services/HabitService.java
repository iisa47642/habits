package ru.habits.spring.habits.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.habits.spring.habits.models.*;
import ru.habits.spring.habits.repositories.HabitProgressRepository;
import ru.habits.spring.habits.repositories.HabitRepository;
import ru.habits.spring.habits.repositories.UserRepository;
import ru.habits.spring.habits.utils.HabitNotFoundException;
import ru.habits.spring.habits.utils.UserNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitProgressRepository habitProgressRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    @Autowired

    public HabitService(HabitRepository habitRepository,
                        HabitProgressRepository habitProgressRepository,
                        UserRepository userRepository,
                        FileStorageService fileStorageService) {
        this.habitRepository = habitRepository;
        this.habitProgressRepository = habitProgressRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void createHabit(Habit habit) {
        enrichHabit(habit);
        User author = habit.getAuthor();
        habit.addUser(author);
        habit.setAuthor(author);
        author.addHabit(habit);

        habitRepository.save(habit);
    }

    @Transactional
    public void createHabits(List<Habit> habits) {
        for (Habit habit : habits) {
            enrichHabit(habit);
            User author = habit.getAuthor();
            habit.addUser(author);
            habit.setAuthor(author);
            author.addHabit(habit);
        }

        habitRepository.saveAll(habits);
    }

    private void enrichHabit(Habit habit) {
        habit.setCreatedAt(LocalDate.now());
        habit.setUpdatedAt(LocalDateTime.now());
    }

    public List<Habit> getHabits() {
        return habitRepository.findAll();
    }

    public Habit getHabitWithUsersById(long id) {
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new HabitNotFoundException("Habit not found"));
        Hibernate.initialize(habit.getUsers());
        return habit;
    }

    public List<HabitProgress> getHabitProgresses(long id, User user) {
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new HabitNotFoundException("Habit not found"));
        return habitProgressRepository.findByUserAndHabitWithAttachments(user, habit);

//        if (habitProgresses != null) {
//            habitProgresses.forEach(progress ->
//                    Hibernate.initialize(progress.getAttachments()));
//        }
    }

    public List<HabitProgress> getHabitProgresses(long id, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return getHabitProgresses(id, user);
    }

    @Transactional
    public void createHabitProgress(HabitProgressWithFilesDTO habitProgressWithFilesDTO,
                                    User user, long id) {
        HabitProgress habitProgress = new HabitProgress();

        habitProgress.setUser(user);
        habitProgress.setHabit(habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException("Habit not found")));
        habitProgress.setProgress(habitProgressWithFilesDTO.getProgress());


        List<Attachment> attachments = habitProgressWithFilesDTO.getAttachmentFiles()
                .stream().map(file -> createAttachment(file, habitProgress)).toList();

        habitProgress.setAttachments(attachments);

        habitProgressRepository.save(enrichHabitProgress(habitProgress));
    }

    private Attachment createAttachment(MultipartFile multipartFile, HabitProgress habitProgress) {
        String fileUrl = fileStorageService.saveFile(multipartFile);

        Attachment attachment = new Attachment();

        attachment.setHabitProgress(habitProgress);
        attachment.setUrl(fileUrl);
        attachment.setType(1);
        return attachment;
    }

    private HabitProgress enrichHabitProgress(HabitProgress habitProgress) {
        habitProgress.setSentAt(LocalDateTime.now());
        return habitProgress;
    }

    public void inviteUserToHabit(long id, long userId) {
        Habit habit = habitRepository.findById(id).orElseThrow(
                () -> new HabitNotFoundException("Habit not found"));

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        user.addHabit(habit);
        habit.addUser(user);
    }
}
