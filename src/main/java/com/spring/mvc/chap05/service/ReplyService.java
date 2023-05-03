package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.ReplyListResponseDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.entity.Reply;
import com.spring.mvc.chap05.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyMapper replyMapper;

    // 댓글 목록 조회 서비스
    public ReplyListResponseDTO getList(long boardNo, Page page) {
        int count = replyMapper.count(boardNo);
        return ReplyListResponseDTO.builder()
                .count(count)
                .pageInfo(new PageMaker(page, count))
                .replies(replyMapper.findAll(boardNo, page).stream()
                        .map(ReplyListResponseDTO.ReplyDetailResponseDTO::new)
                        .collect(Collectors.toList()))
                .build();
    }

}
