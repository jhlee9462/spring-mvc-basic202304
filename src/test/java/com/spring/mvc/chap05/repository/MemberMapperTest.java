package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper mapper;

    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("회원가입에 성공해야 한다.")
    void saveTest() {
        Member member = Member.builder()
                .account("lion")
                .password(encoder.encode("aaa1234"))
                .name("이사자")
                .email("lion@naver.com")
                .build();

        boolean flag = mapper.save(member);

        assertTrue(flag);
    }

    @Test
    @DisplayName("peach라는 계정명으로 회원을 조회하면 그 회원의 이름이 천도복숭아여야 한다.")
    void findMemberTest() {
        Member member = mapper.findMember("peach");

        assertEquals("천도복숭아", member.getName());
    }

    @Test
    @DisplayName("account 타입의 peach 중복검사를 했을 때 결과가 1이 나와야 한다.")
    void isDuplicateTest() {
        int result = mapper.isDuplicate("account", "peach");

        assertEquals(1, result);
    }

    @Test
    @DisplayName("비밀번호가 암호화 되어야 한다.")
    void encodingTest() {

        // 인코딩 전 패스워드
        String rawPassword = "abc1234!";

        // 인코딩 후 패스워드
        String encoded = encoder.encode(rawPassword);

        System.out.println("rawPassword = " + rawPassword);
        System.out.println("encoded = " + encoded);
    }

}