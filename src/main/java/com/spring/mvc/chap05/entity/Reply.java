package com.spring.mvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

/*
CREATE TABLE tbl_reply (
    reply_no INT(10) AUTO_INCREMENT,
    repLy_text VARCHAR(1000) NOT NULL,
    reply_writer VARCHAR(100) NOT NULL,
    reply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    board_no INT(10),
    CONSTRAINT pk_reply PRIMARY KEY (reply_no),
    CONSTRAINT fk_reply FOREIGN KEY (board_no) REFERENCES tb_board (board_no)
                       ON DELETE CASCADE
);
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
@Builder
public class Reply {

    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;
    private String account;
    private String profileImage;

}
