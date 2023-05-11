package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.LoginUserResponseDTO;
import com.spring.mvc.chap05.dto.SignupRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.spring.mvc.chap05.service.LoginResult.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    private final PasswordEncoder encoder;

    // 회원가입 처리 서비스
    public boolean join(SignupRequestDTO dto) {

        // dto를 entity로 변환
        Member member = Member.builder()
                .account(dto.getAccount())
                .email(dto.getEmail())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .build();

        // 매퍼에게 회원정보 저장해서 저장명령
        return memberMapper.save(member);
    }

    // 중복검사 서비스 처리
    public boolean checkSignUpValue(String type, String keyword) {
        return memberMapper.isDuplicate(type, keyword) == 1;
    }

    public LoginResult authenticate(LoginRequestDTO dto) {

        Member foundMember = memberMapper.findMember(dto.getAccount());

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
        session.setAttribute("login", LoginUserResponseDTO.builder()
                        .account(member.getAccount())
                        .nickName(member.getName())
                        .email(member.getEmail())
                .build());

        // 세션의 수명을 설정
        session.setMaxInactiveInterval(60 * 60);

    }

    // 멤버 정보를 가져오는 서비스기능
    public Member getMember(String account) {
        return memberMapper.findMember(account);
    }
}
