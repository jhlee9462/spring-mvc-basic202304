package com.spring.mvc.interceptor;

import com.spring.mvc.chap05.repository.BoardMapper;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtil.isAdmin;
import static com.spring.mvc.util.LoginUtil.isMine;

// 인터셉터 : 하위 컨트롤러에 요청이 들어가기 전, 후에 공통으로 검사할 일들을 정의해놓는 클래스
// 게시판 관련 인가 처리
@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 로그인을 했는지 확인할 것임.
        // 로그인을 안했으면 로그인페이지로 강제로 리다이렉션 할 것임.
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        if (!LoginUtil.isLogin(session)) {
            log.info("this request( {} ) denied", uri);
            response.sendRedirect("/members/sign-in");
            return false;
        }

        // 삭제요청을 한다면 자기가 쓴 글 또는 관리자인지 체크

        /*
            1. 우선 로그인계정과 삭제하려는 게시물의 계정명이 일치해야함
            2. 로그인계정은 세션에서 구할 수 있음
            3. 삭제하려는 게시물의 계정은 어캐구함?
                -> 삭제 요청에는 글번호 정보가 있다.
                -> 글번호를 DB에 조회해서 계정명을 얻어낸다.
         */
        // 삭제요청인지 확인

        if (uri.contains("delete") && (!isMine(session, mapper.findOne(Integer.parseInt(request.getParameter("bno"))).getAccount()) && !isAdmin(session))) {
            log.info("this request ( {} ) denied", uri);
            response.sendRedirect("/access-denied");
            return false;
        }

            return true;
        }
    }
