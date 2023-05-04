package com.spring.mvc.chap05.dto;

import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
@Builder
// RequestDTO는 클라이언트가 제대로 값을 보냈는지 검증해야함
public class ReplyPostRequestDTO {

    // 필드명은 클라이언트 개발자와 상의해야 함
    @NotBlank // 필수값
    private String text; // 댓글 내용

    @NotBlank
    @Size(min = 2, max = 8)
    private String author; // 댓글 작성자명

    /*
        @NotNull    - null을 허용하지 않음
        @NotBlank   - null + ""을 허용하지 않음
        @NotEmpty   - ""을 허용하지 않음
     */
    @NotNull
    @Min(0) @Max(Long.MAX_VALUE)
    private Long boardNo; // 글 번호

    // dto를 entity로 바꿔서 리턴하는 메서드
    public Reply toEntity() {
        return Reply.builder()
                .replyText(this.text)
                .replyWriter(this.author)
                .boardNo(this.boardNo)
                .build();
    }

}
