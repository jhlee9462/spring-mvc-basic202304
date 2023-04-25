package com.spring.mvc.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    @DisplayName("사람의 이름과 나이 정보를 DB person table에 잘 삽입해야 한다.")
    void saveTest() {
        // given
        Person p = new Person();
        p.setPersonName("천지호");
        p.setPersonAge(33);

        // when
        repository.save(p);

        // then

    }

    @Test
    @DisplayName("사람의 이름과 나이 정보를 id를 바탕으로 수정해야 한다")
    void updateTest() {
        // given
        Person p = new Person();
        p.setPersonName("수정수정");
        p.setPersonAge(99);
        p.setId(1L);

        // when
        repository.update(p);

        // then
    }

    @Test
    @DisplayName("사람 정보를 id 를 바탕으로 삭제해야 한다.")
    void deleteTest() {
        // given
        Person p = new Person();
        p.setId(1L);

        // when
        repository.delete(p);

        // then
    }

    @Test
    void findAllTest() {
        repository.findAll();
    }

    @Test
    @DisplayName("id 를 받아서 사람을 리턴해야 한다. 없으면 null 을 반환한다.")
    void findOneTest() {

        Person p = repository.findOne(99L);
        System.out.println("p = " + p);

    }

}