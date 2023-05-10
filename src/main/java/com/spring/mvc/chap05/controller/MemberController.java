package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.SignupRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/members")
public class MemberController {

    // 회원 가입 요청
    // 회원가입 양식 요청
    @GetMapping("/sign-up")
    public String signup() {
        log.info("/members/signup GET - forwarding to jsp");

        return "members/sign-up";
    }

    // 회원가입 처리 요청
    @PostMapping("/sign-up")
    public void signup(SignupRequestDTO dto) {
        log.info("/members/signup POST - {}", dto);
    }

}
