package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.dto.AutoLoginDTO;
import com.spring.mvc.chap05.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    // 회원 가입
    boolean save(Member member);

    // 회원 정보 조회
    Member findMember(String account);

    // 중복 체크(account, email) 기능
    int isDuplicate(String type, String keyword);

    // 자동로그인 관련 속성 추가 기능
    void saveAutoLogin(AutoLoginDTO dto);

    // 쿠기값(세션아이디)으로 회원을 조회하는 기능
    Member findMemberByCookie(String sessionId);
}
