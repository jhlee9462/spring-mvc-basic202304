package com.spring.mvc.chap05.dto;

import lombok.*;

@Setter @Getter @Builder
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class LoginUserResponseDTO {

    private String account;
    private String nickName;
    private String email;

}
