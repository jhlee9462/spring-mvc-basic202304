package com.spring.mvc.etc;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void tttt() {

        Employee e = Employee.builder()
                .position("대리")
                .empName("홍길동")
                .hireDate(LocalDate.of(2019, 3, 15))
                .empId(999)
                .build();

        System.out.println("e = " + e);

    }

}