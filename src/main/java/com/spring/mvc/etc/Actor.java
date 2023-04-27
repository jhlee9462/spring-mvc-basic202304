package com.spring.mvc.etc;

import lombok.*;

@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    private String actorName;
    private int actorAge;
    private boolean hasPhone;

}
