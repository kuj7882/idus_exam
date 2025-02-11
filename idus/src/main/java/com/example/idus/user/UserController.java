package com.example.idus.user;

import com.example.idus.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

//    @GetMapping("/verify")
//    public ResponseEntity<String> verify(@RequestParam("uuid") String uuid) {
//        userService.verify();
//        return ResponseEntity.ok("User verification successful.");
//    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserDto.SignupRequest dto) {
        userService.signup(dto);
    }

}
