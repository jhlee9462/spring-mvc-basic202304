package com.spring.mvc.chap05.dto.search;

import com.spring.mvc.chap05.dto.page.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString
@Setter
public class Search extends Page {

    // 검색 타입, 검색 키워드
    private String type;
    private String keyword;

    public Search() {
        this.type = "";
        this.keyword = "";
    }
}
