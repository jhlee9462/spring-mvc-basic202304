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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.chap05.service.LoginResult.SUCCESS;
import static com.spring.mvc.util.LoginUtil.isAutoLogin;
import static com.spring.mvc.util.LoginUtil.isLogin;

@Controller
@Slf4j
@RequiredArgsConstructor
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
    public String signIn(HttpServletRequest request) {
        log.info("/members/sign-in GET - forwarding to jsp");

        // 요청 정보 헤더 안에는 Referer라는 키가 있는데
        // 여기 값은 이 페이지로 들어올 때 어디에서 왔는지에 대한
        // URI 정보가 기록되어 있음
        String referer = request.getHeader("Referer");
        log.info("referer : {}", referer);

        return "members/sign-in";
    }

    // 로그인 검증 요청
    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto,
                         // 리다이렉션시 2번째 응답에 데이터를 보내기 위함
                         RedirectAttributes attributes,
                         HttpServletResponse response,
                         HttpServletRequest request) {
        log.info("/members/sign-in POST - {}", dto);

        LoginResult result = service.authenticate(dto, request.getSession(), response);

        // 로그인 성공시
        if (result == SUCCESS) {

            // 서버에서 세션에 로그인 정보를 저장
            service.maintainLoginState(request.getSession(), dto.getAccount());

//            // 쿠키 만들기
//            Cookie loginCookie = new Cookie("login", "홍길동");
//
//            // 쿠키 세팅
//            // 쿠키의 유효 범위 설정
//            loginCookie.setPath("/");
//
//            // 쿠키의 수명 설정
//            loginCookie.setMaxAge(60 * 60 * 24);
//
//            // 쿠키를 응답시에 실어서 클라이언트에게 전송
//            response.addCookie(loginCookie);

            return "redirect:/";
        }

        attributes.addFlashAttribute("msg", result);

        // 로그인 실패시
        return "redirect:/members/sign-in";
    }

    // 로그아웃 요청 처리
    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        // 로그인중인지 확인
        if (isLogin(session)) {
            // 자동로그인 상태라면 해제한다.
            if (isAutoLogin(request)) {
                service.autoLoginClear(request, response);
            }

            // 세션에서 login 정보를 제거
            session.removeAttribute("login");

            // 세션을 아예 초기화 (세션만료 시간)
            session.invalidate();

            return "redirect:/";
        }

        return "redirect:/members/sign-in";
    }
}
