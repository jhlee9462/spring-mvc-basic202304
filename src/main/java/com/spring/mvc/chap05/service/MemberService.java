package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.AutoLoginDTO;
import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.LoginUserResponseDTO;
import com.spring.mvc.chap05.dto.SignupRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static com.spring.mvc.chap05.service.LoginResult.*;
import static com.spring.mvc.util.LoginUtil.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    private final PasswordEncoder encoder;

    // 회원가입 처리 서비스
    public boolean join(final SignupRequestDTO dto, final String savePath) {

        // dto를 entity로 변환
        Member member = Member.builder()
                .account(dto.getAccount())
                .email(dto.getEmail())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .profileImage(savePath)
                .build();

        // 매퍼에게 회원정보 저장해서 저장명령
        return memberMapper.save(member);
    }

    // 중복검사 서비스 처리
    public boolean checkSignUpValue(String type, String keyword) {
        return memberMapper.isDuplicate(type, keyword) == 1;
    }

    public LoginResult authenticate(LoginRequestDTO dto, HttpSession session, HttpServletResponse response) {

        Member foundMember = memberMapper.findMember(dto.getAccount());

        if (dto.isAutoLogin()) {
            // 1. 쿠키 생성 - 쿠키값에 세션 아이디를 저장
            String sessionId = session.getId();
            Cookie autoLoginCookie = new Cookie(AUTO_LOGIN_COOKIE, sessionId);

            // 2. 쿠키 세팅 - 수명이랑 사용경로
            int limitTime = 90 * 60 * 60 * 24;
            autoLoginCookie.setMaxAge(limitTime);
            autoLoginCookie.setPath("/"); // 전체 경로

            // 3. 쿠키를 클라이언트에 응답전송
            response.addCookie(autoLoginCookie);

            // 4. DB에도 쿠키에 저장된 값과 수명을 저장
            memberMapper.saveAutoLogin(
                    AutoLoginDTO.builder()
                            .sessionId(sessionId)
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .account(dto.getAccount())
                            .build()
            );
        }

        if (foundMember == null) {
            log.info("{} - 회원가입 안했음 ㅋㅋ", dto.getAccount());
            return NO_ACC;
        } else if (!encoder.matches(dto.getPassword(), foundMember.getPassword())) {
            log.info("{} - 비밀번호 틀렸음 ㅋㅋ", dto.getAccount());
            return NO_PW;
        } else {
            log.info("{} - 님 로그인 성공~", dto.getAccount());
            return SUCCESS;
        }

    }

    public void maintainLoginState(HttpSession session, String account) {

        // 로그인이 성공하면 세션에 로그인한 회원의 정보들을 저장
        /*
            로그인시 클라이언트에게 전달할 회원정보
            - 닉네임
            - 프로필 사진
            - 마지막 로그인 시간
         */
        // 현재 로그인한 사람의 모든 정보
        Member member = getMember(account);

        // DTO로 만들어서 세션에 담기
        session.setAttribute(LOGIN_KEY, LoginUserResponseDTO.builder()
                        .account(member.getAccount())
                        .nickName(member.getName())
                        .email(member.getEmail())
                        .auth(member.getAuth().toString())
                        .profile(member.getProfileImage())
                .build());

        // 세션의 수명을 설정
        session.setMaxInactiveInterval(60 * 60);

    }

    // 멤버 정보를 가져오는 서비스기능
    public Member getMember(String account) {
        return memberMapper.findMember(account);
    }

    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {
        // 1. 자동로그인 쿠키를 가져온다.
        Cookie c = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);

        // 2. 쿠키를 삭제한다.
        // -> 쿠키의 수명을 0초로 만들어서 다시 클라이언트에게 응답
        if (c != null) {
            c.setMaxAge(0);
            c.setPath("/");
            response.addCookie(c);

            // 3. 데이터베이스에도 자동로그인을 해제한다
            memberMapper.saveAutoLogin(
                    AutoLoginDTO.builder()
                            .sessionId("none")
                            .limitTime(LocalDateTime.now())
                            .account(getCurrentLoginMemberAccount(request.getSession()))
                            .build()
            );
        }
    }
}
