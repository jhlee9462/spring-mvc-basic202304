package com.spring.mvc.chap05.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @Builder
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class AutoLoginDTO {

    private String sessionId;
    private LocalDateTime limitTime;
    private String account;

}
