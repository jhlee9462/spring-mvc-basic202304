package com.spring.mvc.chap05.api;

import com.spring.mvc.chap05.dto.ReplyListResponseDTO;
import com.spring.mvc.chap05.dto.ReplyPostRequestDTO;
import com.spring.mvc.chap05.dto.ReplyPutRequestDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.entity.Reply;
import com.spring.mvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/replies")
@Slf4j
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 목록 조회 요청
    // URL : /api/v1/replies/3/page/1
    //          3번 게시물 댓글 목록 1페이지 줘
    @GetMapping("/{boardNo}/page/{pageNo}")
    public ResponseEntity<?> replyList(@PathVariable long boardNo, @PathVariable int pageNo) {

        log.info("/api/v1/replies/{}/page/{} : GET!!", boardNo, pageNo);

        Page page = new Page();
        page.setAmount(10);
        page.setPageNo(pageNo);
        ReplyListResponseDTO list = replyService.getList(boardNo, page);

        return ResponseEntity.ok().body(list);
    }

    // 댓글 등록 요청
    @PostMapping
    public ResponseEntity<?> create(
            // 요청 메세지 바디에 JSON으로 보내주세요
            @Validated @RequestBody ReplyPostRequestDTO dto,
            BindingResult result // 검증결과를 가진 객체
    ) {
        // 입력값 검증에 걸리면 4xx 상태코드 리턴
        if (result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        log.info("/api/v1/replies : POST! ");
        log.info("param: {} ", dto);

        // 서비스에 비즈니스 로직 처리 위임
        try {
            ReplyListResponseDTO responseDTO = replyService.register(dto);

            // 클라이언트에 응답하기
            return ResponseEntity.ok().body(responseDTO);

        } catch (SQLException e) {

            log.warn("500 Status code response!! caused by: {}", e.getMessage());
            // 문제발생 상황을 클라이언트에게 전달

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    // 댓글 삭제 요청
    @DeleteMapping("/{replyNo}")
    public ResponseEntity<?> remove(@PathVariable(required = false) Long replyNo) {

        if (replyNo == null) return ResponseEntity.badRequest().body("댓글 번호를 보내주세요!");

        log.info("/api/v1/replies/{} : DELETE! ", replyNo);

        ReplyListResponseDTO responseDTO = null;
        try {
            responseDTO = replyService.delete(replyNo);
            return ResponseEntity.ok().body(responseDTO);

        } catch (SQLException e) {

            return ResponseEntity.internalServerError().body("죄송합니다.");
        }

    }

    // 댓글 수정 요청
    @PutMapping
    public ResponseEntity<?> modify(
        @Validated @RequestBody ReplyPutRequestDTO dto
        , BindingResult result
    ) {

        if (result.hasErrors()) {
            log.warn("값 검증 실패!!");
            return ResponseEntity.badRequest().body(result.toString());
        }

        try {
            ReplyListResponseDTO responseDTO = replyService.modify(dto);

            return ResponseEntity.ok().body(responseDTO);
        } catch (SQLException e) {
            log.warn(e.getMessage());
            return ResponseEntity.internalServerError().body("수정 실패! 에러 메세지 " + e.getMessage());
        }

    }

}
