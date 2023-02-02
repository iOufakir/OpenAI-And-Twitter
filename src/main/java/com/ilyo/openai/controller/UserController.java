package com.ilyo.openai.controller;


import com.ilyo.openai.dto.CommentRequest;
import com.ilyo.openai.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users/{userId}")
@PreAuthorize("hasAuthority('" + "USER" + "')")
@CrossOrigin(origins = {"http://localhost", "https://www.youtube.com", "https://youtu.be", "https://twitter.com"})
public class UserController {

    private final UserService userService;

    @PostMapping("/comment/check")
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public boolean checkCommentNegativity(@Valid @RequestBody CommentRequest request) {
        return userService.isTextHarmful(request.text()).get();
    }
}

