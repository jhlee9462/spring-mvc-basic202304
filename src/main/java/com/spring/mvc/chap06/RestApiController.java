package com.spring.mvc.chap06;

import com.spring.mvc.jdbc.Person;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rests")
public class RestApiController {

    @GetMapping("/hello")
    public String hello() {
        return "안녕하세요!";
    }

    @GetMapping("/foods")
    public List<String> foods() {
        return List.of("탕수육", "족발", "마라탕");
    }

    @GetMapping("/person")
    public Person person() {
        return Person.builder()
                .id(1)
                .personAge(3)
                .personName("루피")
                .build();
    }

    @GetMapping("/person-list")
    public ResponseEntity<?> personList() {
        List<Person> person = List.of(
                Person.builder()
                        .id(1L)
                        .personAge(1)
                        .personName("한짤")
                        .build(),
                Person.builder()
                        .id(2L)
                        .personAge(2)
                        .personName("두짤")
                        .build(),
                Person.builder()
                        .id(3L)
                        .personAge(3)
                        .personName("세짤")
                        .build()
        );
        return ResponseEntity.ok().body(person);
    }

    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(
            @RequestParam(required = false) Double height,
            @RequestParam(required = false) Double weight) {

        if (height == null || weight == null)
            return ResponseEntity.badRequest().body("키랑 몸무게 좀 보내 이새갸");

        double bmi = weight / (height / 100) * (height / 100);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("fruits", "melon");
        httpHeaders.add("hobby", "coding");

        return ResponseEntity.ok().headers(httpHeaders).body(bmi);
    }
}
