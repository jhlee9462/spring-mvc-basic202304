package com.spring.mvc.jdbc;

import lombok.*;

// 엔터티 : 이 클래스는 데이터베이스 테이블과의 연동을 위한 객체
// DB table의 컬럼과 1 : 1 로 매칭되는 필드를 적어주세요
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
public class Person {

    private long id;
    private String personName;
    private int personAge;

}
