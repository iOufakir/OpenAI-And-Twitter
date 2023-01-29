package com.ilyo.openai.controller;


import com.ilyo.openai.dto.CommentRequest;
import com.ilyo.openai.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users/{userId}")
@PreAuthorize("hasAuthority('" + "USER" + "')")
@CrossOrigin(origins = {"https://www.youtube.com", "https://youtu.be"})
public class UserController {

    private final UserService userService;

    @PostMapping("/comment/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkCommentNegativity(@Valid @RequestBody CommentRequest request) {
        return userService.isHarmfulText(request.text());
    }
}

