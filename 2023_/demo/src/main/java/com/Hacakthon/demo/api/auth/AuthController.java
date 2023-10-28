package com.Hacakthon.demo.api.auth;

import com.Hacakthon.demo.api.user.UserEntity;
import com.Hacakthon.demo.global.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseDto<UserEntity> signUp(@RequestBody SignUpDto requestBody){
        ResponseDto<UserEntity> result = authService.signUp(requestBody);
        return result;
    }


    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody){
        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

//    @PostMapping("/logout")
//    public ResponseDto<String> logout(@RequestBody LogoutDto requestBody){
//        ResponseDto<String> result = authService.logout(requestBody);
//        return result;
//    }

}
