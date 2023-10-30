package com.Hacakthon.demo.api.favor;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="favor")
@Table(name="favor")
public class FavorEntity {
    @Id
    private String userEmail;
    private String favor1;
    private String favor2;
    private String favor3;
    private String favor4;
    private String favor5;
}
