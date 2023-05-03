package com.spring.mvc.chap05.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReplyListResponseDTO {

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString @EqualsAndHashCode
    public static class ReplyDetailResponseDTO {
        private long rno;
        private String text;
        private String writer;

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime regDate;

        // 엔터티를 DTO로 변환하는 생성자
        public ReplyDetailResponseDTO(Reply reply) {
            this.rno = reply.getReplyNo();
            this.text = reply.getReplyText();
            this.writer = reply.getReplyWriter();
            this.regDate = reply.getReplyDate();
        }
    }

    private int count; // 총 댓글 수
    private PageMaker pageInfo; // 페이지 정보
    private List<ReplyDetailResponseDTO> replies; // 댓글 리스트

}
