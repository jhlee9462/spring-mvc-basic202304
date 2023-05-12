package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.BoardDetailResponseDTO;
import com.spring.mvc.chap05.dto.BoardListResponseDTO;
import com.spring.mvc.chap05.dto.BoardModifyDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.search.Search;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardMapper;
import com.spring.mvc.chap05.repository.BoardRepository;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class BoardService {

//    private final BoardRepository boardRepository;

    private final BoardMapper mapper;

    // 중간처리 기능 자유롭게 사용
    // 목록 중간처리
    public List<BoardListResponseDTO> getList(Search page) {

        return mapper.findAll(page)
                .stream()
                .map(BoardListResponseDTO::new)
                .collect(toList())
                ;
    }

    // 글 등록 중간처리
    public boolean register(BoardWriteRequestDTO dto, HttpSession session) {
        Board board = new Board(dto);
        board.setAccount(LoginUtil.getCurrentLoginMemberAccount(session));
        return mapper.save(board);
    }

    public boolean delete(int bno) {
        return mapper.deleteByNo(bno);
    }

    public BoardDetailResponseDTO getDetail(int bno) {

        Board board = mapper.findOne(bno);
        // 조회수 상승 처리
        board.setViewCount(board.getViewCount() + 1);
        mapper.modify(new BoardModifyDTO(board));

        return new BoardDetailResponseDTO(board);
    }

    public BoardModifyDTO getModify(int bno) {
        return new BoardModifyDTO(mapper.findOne(bno));
    }

    public boolean modify(BoardModifyDTO dto) {
        return mapper.modify(dto);
    }

    // 정렬
    public List<BoardListResponseDTO> getList(String way, Search page) {

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

        return mapper.findAll(page)
                .stream()
                .sorted(comparator)
                .map(BoardListResponseDTO::new)
                .collect(toList());
    }

    public List<BoardListResponseDTO> getListByKeyword(String keyword, Search page) {
        return mapper.findAll(page)
                .stream()
                .filter(board -> board.getTitle().contains(keyword) || board.getContent().contains(keyword))
                .map(BoardListResponseDTO::new)
                .collect(toList());
    }

    public int getCount(Search search) {
        return mapper.count(search);
    }
}
