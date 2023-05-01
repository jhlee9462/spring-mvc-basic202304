package com.spring.mvc.chap05.entity;

import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import lombok.*;

import java.time.LocalDateTime;

/*
CREATE TABLE tb_board (
    board_no INT(10) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(80) NOT NULL,
    content VARCHAR(2000),
    view_count INT(10) DEFAULT 0,
    reg_date_time DATETIME DEFAULT CURRENT_TIMESTAMP -- INSERT 당시의 시간
);
 */

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    private int boardNo; // 게시글 번호
    private String title; // 제목
    private String content; // 내용
    private int viewCount; // 조회수
    private LocalDateTime regDateTime; // 작성일자시간

    public Board(int boardNo, String title, String content) {
        this.boardNo = boardNo;
        this.title = title;
        this.content = content;
        this.regDateTime = LocalDateTime.now();
    }

    public Board(BoardWriteRequestDTO dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.regDateTime = LocalDateTime.now();
    }
}
