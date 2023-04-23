package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.dto.BoardModifyDTO;
import com.spring.mvc.chap05.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class BoardRepositoryImpl
        implements BoardRepository {

    private final static Map<Integer, Board> boardMap;
    private static int sequence;

    static {
        boardMap = new HashMap<>();
        Board b1 = new Board(++sequence, "돈까스 레시피", "그냥 이마트에서 사서 에어프라이 돌려라~");
        Board b2 = new Board(++sequence, "관종의 조건", "이 세상은 나를 중심으로 돌아간다라는 마음으로 행동해라ㅋㅋ");
        Board b3 = new Board(++sequence, "이마트 영업시간", "10시에 마감하는걸로 바뀌었나요?? 마감털이 몇시에 가야되죠?? 하.....");
        boardMap.put(b1.getBoardNo(), b1);
        boardMap.put(b2.getBoardNo(), b2);
        boardMap.put(b3.getBoardNo(), b3);
    }

    @Override
    public List<Board> findAll() {
        return boardMap.values()
                .stream()
                .sorted(comparing(Board::getBoardNo).reversed())
                .collect(toList())
                ;
    }

    @Override
    public Board findOne(int boardNo) {
        return boardMap.get(boardNo);
    }

    @Override
    public boolean save(Board board) {
        board.setBoardNo(++sequence);
        boardMap.put(board.getBoardNo(), board);
        return true;
    }

    @Override
    public boolean deleteByNo(int boardNo) {
        boardMap.remove(boardNo);
        return true;
    }

    @Override
    public boolean modify(BoardModifyDTO dto) {
        Board board = boardMap.get(dto.getBoardNo());

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        return true;
    }

}
