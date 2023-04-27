package com.spring.mvc.etc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    @Test
    void ttttt() {

        Actor actor = Actor.builder()
                .actorName("장동건")
                .hasPhone(false)
                .actorAge(40)
                .build();

    }

}