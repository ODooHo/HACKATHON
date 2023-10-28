package com.Hacakthon.demo.api.auth;

import com.Hacakthon.demo.api.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private UserEntity user;
}
