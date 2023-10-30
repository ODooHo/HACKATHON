package com.Hacakthon.demo.api.auth;

import com.Hacakthon.demo.api.user.UserEntity;
import com.Hacakthon.demo.api.user.UserRepository;
import com.Hacakthon.demo.global.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseDto<UserEntity> signUp(SignUpDto dto){
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        String userNickname = dto.getUserNickname();

        //이메일 중복 확인
        try{
            if (userRepository.existsById(userEmail)){
                return ResponseDto.setFailed("Email already exist!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }

        UserEntity userEntity = modelMapper.map(dto,UserEntity.class);

        String encodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setUserPassword(encodedPassword);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        return ResponseDto.setSuccess("Success",userEntity);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto){
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        UserEntity userEntity = null;

        try{
            userEntity = userRepository.findByUserEmail(userEmail);
            if(userEntity == null){
                return ResponseDto.setFailed("Unknown User!");
            }
            if(!passwordEncoder.matches(userPassword,userEntity.getUserPassword())){
                return ResponseDto.setFailed("Different Password!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error!");
        }
        userEntity.setUserPassword("");


        SignInResponseDto signInResponseDto = new SignInResponseDto(userEntity);
        return ResponseDto.setSuccess("Success",signInResponseDto);
    }

//    public ResponseDto<String> logout(LogoutDto dto){
//        String token = dto.getToken();
//        try{
//            redisTemplate.opsForValue().set(token,"logout",expiration, TimeUnit.MILLISECONDS);
//            redisTemplate.opsForSet().add("Blacklist",token);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseDto.setFailed("DataBase Error (Auth)");
//        }
//
//        return ResponseDto.setSuccess("Success","Logout Completed");
//
//    }




}
