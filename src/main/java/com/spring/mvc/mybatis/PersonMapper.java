package com.spring.mvc.mybatis;

import com.spring.mvc.jdbc.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    // CRUD 에 대한 기능 명세
    // 사람 정보 저장
    boolean save(Person p);

    // 사람 정보 수정
    boolean modify(Person p);

    // 사람 정보 삭제
    boolean remove(long id);

    // 전체 조회
    List<Person> findAll();

    // 개별 조회
    Person findOne(long id);

}
