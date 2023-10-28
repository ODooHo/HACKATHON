package com.Hacakthon.demo.api.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @NotBlank
    private String userEmail;
    @NotBlank
    private String userPassword;


}
