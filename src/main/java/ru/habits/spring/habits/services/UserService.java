package ru.habits.spring.habits.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.habits.spring.habits.dto.KeycloakEventDto;
import ru.habits.spring.habits.models.Code;
import ru.habits.spring.habits.models.User;
import ru.habits.spring.habits.repositories.CodeRepository;
import ru.habits.spring.habits.repositories.UserRepository;
import ru.habits.spring.habits.utils.UserNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;

    @Autowired
    public UserService(UserRepository userRepository, CodeRepository codeRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }


    public List<User> getUsersById(User user) {
        return user.getFriends();
    }

    @Transactional
    public Code createCode(long ownerId, long receiverId) {
        Code code = new Code();
        code.setOwner(userRepository.findById(ownerId).orElseThrow(
                () -> new UserNotFoundException("User not found")));
        code.setReceiver(userRepository.findById(receiverId).orElseThrow(
                () -> new UserNotFoundException("User not found")));
        String codeStr = generateRandomCode();
        code.setCode(codeStr);

        return codeRepository.save(code);
    }

    private String generateRandomCode() {
        String code = "";
        Random random = new Random();
        for (int i=0;  i < 10; i++) {
            char character = (char) random.nextInt();
            code += character;
        }
        return code;
    }

    @Transactional
    public void addFriend(Code code, long receiverId) {
        User receiver = userRepository.findById(receiverId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        if (Objects.equals(code.getReceiver().getId(), receiver.getId()) &&
        code.getCode().equals(codeRepository.findByOwnerAndReceiver(code.getOwner(),receiver).orElseThrow(
                ()-> new UserNotFoundException("User not found")).getCode())) {
            User owner = code.getOwner();
            owner.addFriend(receiver);
            receiver.addFriend(owner);
            userRepository.save(receiver);
        }
    }

    @Transactional
    public void createUser(KeycloakEventDto keycloakEventDto) {
        String keycloakUserId = keycloakEventDto.getUserId();
        String username = keycloakEventDto.getDetails().get("username");

        User user = new User(username, keycloakUserId);
        userRepository.save(user);
    }

    public User getUserBySub(String keycloakUserId) {
        return userRepository.findByKeycloakSub(keycloakUserId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
    }
}
