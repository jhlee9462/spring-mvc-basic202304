package com.spring.mvc.chap05.dto.page;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageMakerTest {

    @Test
    void pageTest() {
        // 클라이언트의 페이지 정보
        Page page = new Page();
        page.setPageNo(27);
        page.setAmount(10);

        PageMaker maker = new PageMaker(page, 284);

        System.out.println("maker = " + maker);
    }

}