package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.BoardDetailResponseDTO;
import com.spring.mvc.chap05.dto.BoardListResponseDTO;
import com.spring.mvc.chap05.dto.BoardModifyDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 중간처리 기능 자유롭게 사용
    // 목록 중간처리
    public List<BoardListResponseDTO> getList() {

        return boardRepository.findAll()
                .stream()
                .map(BoardListResponseDTO::new)
                .collect(toList())
                ;
    }

    // 글 등록 중간처리
    public boolean register(BoardWriteRequestDTO dto) {
        return boardRepository.save(new Board(dto));
    }

    public boolean delete(int bno) {
        return boardRepository.deleteByNo(bno);
    }

    public BoardDetailResponseDTO getDetail(int bno) {

        Board board = boardRepository.findOne(bno);
        // 조회수 상승 처리
        board.setViewCount(board.getViewCount() + 1);

        return new BoardDetailResponseDTO(board);
    }

    public BoardModifyDTO getModify(int bno) {
        return new BoardModifyDTO(boardRepository.findOne(bno));
    }

    public boolean modify(BoardModifyDTO dto) {
        return boardRepository.modify(dto);
    }

    // 정렬
    public List<BoardListResponseDTO> getList(String way) {

        Comparator<Board> comparator;

        switch (way) {
            case "title":
                comparator = Comparator.comparing(Board::getTitle);
                break;
            case "regTime":
                comparator = Comparator.comparing(Board::getRegDateTime).reversed();
                break;
            case "viewCount":
                comparator = Comparator.comparing(Board::getViewCount).reversed();
                break;
            default:
                comparator = Comparator.comparing(Board::getBoardNo);
                break;
        }

        return boardRepository.findAll()
                .stream()
                .sorted(comparator)
                .map(BoardListResponseDTO::new)
                .collect(toList());
    }
}
