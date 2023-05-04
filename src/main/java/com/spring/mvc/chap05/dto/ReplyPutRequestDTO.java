package com.spring.mvc.chap05.dto;

import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
@Builder
public class ReplyPutRequestDTO {

    @NotNull
    @Min(0) @Max(Long.MAX_VALUE)
    private Long rno;

    @NotNull
    @Min(0) @Max(Long.MAX_VALUE)
    private Long bno;

    @NotBlank
    private String text;

    public Reply toEntity() {
        return Reply.builder()
                .replyNo(rno)
                .replyText(text)
                .boardNo(bno)
                .build();
    }
}
