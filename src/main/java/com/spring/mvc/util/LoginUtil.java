package com.spring.mvc.util;

// 회원 인증 인가 관련 상수와 메서드를 가진 객체

import com.spring.mvc.chap05.dto.LoginUserResponseDTO;
import com.spring.mvc.chap05.entity.Auth;
import com.spring.mvc.chap05.entity.Member;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {

    // 로그인 세션 키
    public static final String LOGIN_KEY = "login";

    // 자동로그인 쿠키 이름
    public static final String AUTO_LOGIN_COOKIE = "auto";

    // 로그인 여부 확인
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGIN_KEY) != null;
    }

    // 자동로그인 여부 확인
    public static boolean isAutoLogin(HttpServletRequest request) {
        return WebUtils.getCookie(request, AUTO_LOGIN_COOKIE) != null;
    }

    // 로그인한 사람의 계정명을 반환하는 메서드
    public static String getCurrentLoginMemberAccount(HttpSession session) {
        return ((LoginUserResponseDTO) session.getAttribute(LOGIN_KEY)).getAccount();
    }

    // 관리자인지 확인해주는 메서드
    public static boolean isAdmin(HttpSession session) {
        return ((LoginUserResponseDTO) session.getAttribute(LOGIN_KEY)).getAuth().equals("ADMIN");
    }

    // 내가 쓴 게시물인지 확인해주는 메서드
    // 로그인한 사람 계정명과 실제 게시물 계정명
    public static boolean isMine(HttpSession session, String account) {
        return ((LoginUserResponseDTO) session.getAttribute(LOGIN_KEY)).getAccount().equals(account);
    }

}
