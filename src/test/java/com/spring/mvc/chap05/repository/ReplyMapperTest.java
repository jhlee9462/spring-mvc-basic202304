package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired
    BoardMapper boardMapper;
    @Autowired
    ReplyMapper replyMapper;

    @Test
    @DisplayName("게시물 300개를 등록하고 각 게시물에 랜덤으로 1000개의 댓글을 나눠서 등록해야 한다.")
    void bulkInsertTest() {

        for (int i = 1; i <= 300; i++) {
            boardMapper.save(Board.builder()
                    .title("재밌는 게시물 " + i)
                    .content("노잼 게시물 내용 " + i)
                    .build());
        }

        assertEquals(300, boardMapper.count(null));

        for (int i = 1; i <= 1000; i++) {
            replyMapper.save(Reply.builder()
                    .replyWriter("잼민이 " + i)
                    .replyText("노잼~" + i)
                    .boardNo((long) (Math.random() * 300) + 1)
                    .build());
        }

    }

    @Test
    @DisplayName("댓글을 3번 게시물에 등록하면 3번 게시물의 총 댓글 수는 4개여야 한다.")
    @Transactional
    @Rollback
    void replySaveTest() {
        boolean flag = replyMapper.save(Reply.builder()
                .replyWriter("개똥이")
                .replyText("아기다리고기다리")
                .boardNo(3)
                .build());

        assertTrue(flag);
        assertEquals(4, replyMapper.count(3));

    }

    @Test
    @DisplayName("댓글 번호가 3번인 댓글을 지운다")
    @Transactional
    @Rollback
    void removeTest() {
        boolean flag = replyMapper.deleteOne(3);

        assertTrue(flag);
        assertNull(replyMapper.findOne(3));
    }

    @Test
    @DisplayName("댓글 번호가 3번인 댓글의 작성자를 길동이로 바꾼다")
    @Transactional
    @Rollback
    void modifyTest() {
        Reply one = replyMapper.findOne(3);
        boolean flag = replyMapper.modify(Reply.builder()
                .replyNo(3)
                .replyWriter("길동이")
                .replyText(one.getReplyText())
                .boardNo(one.getBoardNo())
                .build());

        assertTrue(flag);
        assertEquals("길동이", one.getReplyWriter());
    }

    @Test
    @DisplayName("댓글 번호가 3번인 댓글을 찾으면 작성자가 길동이다.")
    void findOneTest() {
        Reply one = replyMapper.findOne(3);

        assertEquals("잼민이 3", one.getReplyWriter());
    }

    @Test
    @DisplayName("게시물 번호 3번의 댓글 수는 3이어야 한다.")
    void findAllTest() {
        List<Reply> all = replyMapper.findAll(3, new Page());

        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("게시물 번호 3번의 댓글 수는 3이어야 한다.")
    void countTest() {
        int count = replyMapper.count(3);

        assertEquals(3, count);
    }
}