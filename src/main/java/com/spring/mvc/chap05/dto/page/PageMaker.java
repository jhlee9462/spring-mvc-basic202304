package com.spring.mvc.chap05.dto.page;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageMaker {

    // 한번에 그려낼 페이지 수
    // 1 ~ 5, 6 ~ 10
    private static final int PAGE_COUNT = 5;

    // 화면 렌더링시 페이지의 시작값과 끝값
    private int begin, end;

    // 마지막 페이지
    private int realEnd;

    // 이전, 다음 버튼 활성화 여부
    private boolean prev, next;

    // 현재 요청 페이지 정보
    private Page page;

    // 총 게시물 수
    private int totalCount;

    public PageMaker(Page page, int totalCount) {
        this.page = page;
        this.totalCount = totalCount;
        makePageInfo();
    }

    // 페이지 계산 알고리즘
    private void makePageInfo() {

        // 1. end 값 계산
        // 올림처리 ( 현재 위치한 페이지번호 / 한 화면에 배치할 페이지 수 ) * 한 화면에 배치할 페이지 수
        this.end = (int) (Math.ceil((double) page.getPageNo() / PAGE_COUNT) * PAGE_COUNT);

        // 2. begin 값 계산
        this.begin = end - PAGE_COUNT + 1;

        /*

        - 총 게시물수가 237개고, 한 화면당 10개의 게시물을 배치하고 있다면
          페이지 구간은

          1 ~ 10페이지 구간 : 게시물 100개
          11 ~ 20페이지 구간: 게시물 100개
          21 ~ 24페이지 구간: 게시물 37개

        - 마지막 페이지 구간에서는 보정이 필요함.

        - 마지막 구간 끝페이지 보정 공식:
          올림처리(총 게시물 수 / 한 페이지당 배치할 게시물 수)

         */
        realEnd = (int) Math.ceil((double) totalCount / page.getAmount());

        // 마지막 페이지 구간에서만 end 보정이 일어나야 함
        if (realEnd < this.end) this.end = realEnd;

        // 이전 버튼 활성화 여부
        this.prev = begin > 1;
        this.next = end < realEnd;

    }

}
