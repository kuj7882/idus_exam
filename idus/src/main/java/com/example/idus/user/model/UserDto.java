package com.example.idus.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class UserDto {
    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    public static class SignupRequest {
        private String username;
        private String nickname;
        private String email;
        private String password;
        private String phonenumber;
        private String gender;

        public User toEntity(String encodedPassword) {
            User user = User.builder()
                    .username(username)
                    .nickname(nickname)
                    .email(email)
                    .password(encodedPassword)
                    .phonenumber(phonenumber)
                    .gender(gender)
                    .build();
            return user;
        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignupResponse {
        private Long idx;
        private String email;

        public static SignupResponse from(User entity) {
            return new SignupResponse(
                    entity.getIdx(), entity.getEmail()
            );
        }
    }
}
