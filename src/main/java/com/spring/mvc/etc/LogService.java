package com.spring.mvc.etc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j // 로그 라이브러리
public class LogService {

    public void showLog() {
        System.out.println("hello log!!");

        log.trace("hello trace!!");
        log.debug("hello debug!!");
        log.info("hello info!!");
        log.warn("hello warn!!");
        log.error("hello error");

    }
}
