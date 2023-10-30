package com.Hacakthon.demo.api.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user")
@Table(name="user")
public class UserEntity {
    @Id
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userGender;
    private Integer userAge;
    private String introduction;
}
