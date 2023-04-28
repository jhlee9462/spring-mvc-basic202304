package com.spring.mvc.chap05.dto;

import com.spring.mvc.chap05.entity.Board;
import lombok.*;

@Getter @ToString
@Setter @NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class BoardModifyDTO {

    private int boardNo;
    private String title;
    private String content;
    private int viewCount;

    public BoardModifyDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount();
    }
}
