package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.SignupRequestDTO;
import com.spring.mvc.chap05.service.LoginResult;
import com.spring.mvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.spring.mvc.chap05.service.LoginResult.SUCCESS;

@Controller
@Slf4j @RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    // 회원 가입 요청
    // 회원가입 양식 요청
    @GetMapping("/sign-up")
    public String signup() {
        log.info("/members/signup GET - forwarding to jsp");

        return "members/sign-up";
    }

    // 회원가입 처리 요청
    @PostMapping("/sign-up")
    public String signup(SignupRequestDTO dto) {
        log.info("/members/signup POST - {}", dto);
        service.join(dto);

        return "redirect:/board/list";
    }

    // 아이디, 이메일 중복 검사
    // 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword) {
        log.info("/members/check?type={}&keyword={} ASYNC GET!", type, keyword);

        boolean flag = service.checkSignUpValue(type, keyword);

        if (flag) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.ok().body(false);
        }
    }

    // 로그인 양식 요청
    @GetMapping("/sign-in")
    public String  signIn() {
        log.info("/members/sign-in GET - forwarding to jsp");
        return "members/sign-in";
    }

    // 로그인 검증 요청
    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto,
                         // 리다이렉션시 2번째 응답에 데이터를 보내기 위함
                         RedirectAttributes attributes) {
        log.info("/members/sign-in POST - {}", dto);

        LoginResult result = service.authenticate(dto);

        // 로그인 성공시
        if (result == SUCCESS)
            return "redirect:/";

        attributes.addFlashAttribute("msg", result);

        // 로그인 실패시
        return "redirect:/members/sign-in";
    }
}
