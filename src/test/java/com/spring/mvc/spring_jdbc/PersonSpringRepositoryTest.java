package com.spring.mvc.spring_jdbc;

import com.spring.mvc.jdbc.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonSpringRepositoryTest {

    @Autowired
    PersonSpringRepository personSpringRepository;

    @Test
    void savePersonTest() {
        // given
        Person p = new Person();
        p.setPersonName("스프링");
        p.setPersonAge(2);

        // when
        personSpringRepository.savePerson(p);
    }

    @Test
    void removePersonTest() {
        // given
        long id = 4L;
        // when
        personSpringRepository.removePerson(4L);
    }

    @Test
    void modifyPersonTest() {
        // given
        Person p = new Person();
        p.setId(2);
        p.setPersonName("비리리");
        p.setPersonAge(15);

        // when
        boolean b = personSpringRepository.modifyPerson(p);
    }

    @Test
    void findAllTest() {
        List<Person> personList = personSpringRepository.findAll();

        assertEquals(5, personList.size());
    }

    @Test
    void findOneTest() {
        Person p = personSpringRepository.findeOne(5L);
        System.out.println("p = " + p);

        assertEquals("춘식이", p.getPersonName());
    }

}