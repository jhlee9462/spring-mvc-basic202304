package com.spring.mvc.mybatis;

import com.spring.mvc.jdbc.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper mapper;

    @Test
    @DisplayName("마이바티스 매퍼로 사람정보 저장에 성공해야 한다.")
    void saveTest() {
        // given
        Person p = Person.builder()
                .personAge(19)
                .personName("한세건")
                .build();

        // when
        boolean result = mapper.save(p);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("마이바티스 매퍼로 사람정보 업데이트에 성공해야 한다.")
    void modifyTest() {
        Person p = Person.builder()
                .id(9)
                .personName("한세건")
                .personAge(20)
                .build();

        boolean result = mapper.modify(p);

        assertTrue(result);
    }

    @Test
    @DisplayName("마이바티스 매퍼로 사람정보 삭제에 성공해야 한다.")
    void deleteTest() {
        boolean result = mapper.remove(9);

        assertTrue(result);
    }

    @Test
    @DisplayName("마이바티스 매퍼로 findAll 하면 길이가 5인 배열이 리턴된다.")
    void findAllTest() {
        List<Person> people = mapper.findAll();

        people.forEach(System.out::println);
        assertEquals(5, people.size());
    }

    @Test
    void findOneTest() {
        Person p = mapper.findOne(7);

        assertEquals("스프링", p.getPersonName());
    }

}