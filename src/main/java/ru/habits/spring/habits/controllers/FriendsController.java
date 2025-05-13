package ru.habits.spring.habits.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.habits.spring.habits.dto.CodeDTO;
import ru.habits.spring.habits.models.Code;
import ru.habits.spring.habits.models.User;
import ru.habits.spring.habits.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public FriendsController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @GetMapping
    public List<User> getFriends(@AuthenticationPrincipal Jwt jwt) {
        String sub = (String) jwt.getClaim("sub");
        User user = userService.getUserBySub(sub);
        return userService.getUsersById(user);
    }

    @GetMapping("/code")
    public CodeDTO getCode(@AuthenticationPrincipal Jwt jwt, long receiverId) {
        String sub = (String) jwt.getClaim("sub");
        User owner = userService.getUserBySub(sub);
        return convertToCodeDTO(userService.createCode(owner.getId(), receiverId));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addFriend(@RequestBody CodeDTO codeDTO, @AuthenticationPrincipal Jwt jwt) {
        String sub = (String) jwt.getClaim("sub");
        User user = userService.getUserBySub(sub);
        userService.addFriend(convertToCode(codeDTO), user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private CodeDTO convertToCodeDTO(Code code) {
        return modelMapper.map(code, CodeDTO.class);
    }

    private Code convertToCode(CodeDTO codeDTO) {
        return modelMapper.map(codeDTO, Code.class);
    }
}
