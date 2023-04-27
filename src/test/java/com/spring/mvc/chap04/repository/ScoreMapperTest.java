package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreMapperTest {

    @Autowired
    ScoreMapper mapper;

    @Test
    void saveTest() {
        Score s = Score.builder()
                .name("한세건")
                .kor(100)
                .eng(75)
                .math(80)
                .build();
        boolean result = mapper.save(s);
        assertTrue(result);
    }

}