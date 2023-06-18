package com.mocProject.shop.DTO;


import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    @NonNull
    private  int id;

    private boolean isAdmin;

    private String userName;

    @Email
    private String email;

    private String password;


}
