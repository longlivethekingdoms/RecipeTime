package com.recipetime.find.Controller.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recipetime.find.Service.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/join")
public class UserJoinApiController {

    private final UserService userService;

    @Autowired
    public UserJoinApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/duplicateCheckID")
    public ResponseEntity<Map<String, String>> checkDuplicateID(@RequestParam String userid) {
        boolean exists = userService.duplicateID(userid);
        String message = exists ? "이미 사용 중인 ID입니다." : "사용 가능한 ID입니다.";
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }

    @GetMapping("/duplicateCheckNickname")
    public ResponseEntity<Map<String, String>> checkDuplicateNickname(@RequestParam String nickname) {
        boolean exists = userService.duplicateNickname(nickname);
        String message = exists ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.";
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }

    @GetMapping("/duplicateCheckEmail")
    public ResponseEntity<Map<String, String>> checkDuplicateEmail(@RequestParam String useremail) {
        boolean exists = userService.duplicateEmail(useremail);
        String message = exists ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.";
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }
}
