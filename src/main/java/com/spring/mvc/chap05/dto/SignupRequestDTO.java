package com.spring.mvc.chap05.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
public class SignupRequestDTO {

    @NotBlank
    private String account;
    @NotBlank
    private String password;
    @NotBlank
    @Size(min=2, max=4)
    private String name;
    @NotBlank
    private String email;

    private MultipartFile profileImage;

}
