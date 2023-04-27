package com.spring.mvc.etc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogServiceTest {

    /*
        - 로그 : 발생시간, 로그 레벨, 파일 저장
        - 로그 라이브러리: logback, log4j, slf4j

        - 로그 레벨
        1. trace : 애플리케이션의 실행 흐픎 세부정보, 디버깅 목적
        2. debug : 변수값, 파라미터값 내부 정보 출력, 디버깅 목적
        ========================== 운영 서버 ▼ =============================
        3. info : 운영환경에서 일반적인 작동 정보들, 시스템 상태, 진행중인 작업 정보
        ========================== 개발 서버 ▲ =============================
        4. warn : 잠재적인 문제상황을 경고, 구성값이 예상 범위를 벗어났거나 시스템 리소스 부족
        5. error : 예외가 발생하거나 기능이 실패했을 때 심각한 문제상황
        6. fatal : 치명적인 오류 시스템이 지속될 수 없는 상황, 즉각 조치가 필요한 경우
     */
    @Autowired
    LogService service;

    @Test
    void ttt() {
        service.showLog();
    }
}