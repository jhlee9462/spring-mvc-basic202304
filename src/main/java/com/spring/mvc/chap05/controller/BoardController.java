package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.BoardListResponseDTO;
import com.spring.mvc.chap05.dto.BoardModifyDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.dto.search.Search;
import com.spring.mvc.chap05.service.BoardService;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // 목록 조회 요청
    @GetMapping("/list")
    public String list(Model model, Search page, HttpServletRequest request) {

//        boolean flag = false;
//
//        // 세션을 확인
//        Object login = request.getSession().getAttribute("login");
//
//        if (login != null) {
//            flag = true;
//        }
//
////        // 쿠키를 확인
////        Cookie[] cookies = request.getCookies();
////        for (Cookie cookie : cookies) {
////            if (cookie.getName().equals("login")) {
////                flag = true;
////            }
////        }
//
//        if (!flag) {
//            return "redirect:/members/sign-in";
//        }

        log.info("/board/list : GET");
        log.info("page : {}", page);
        List<BoardListResponseDTO> responseDTOS
                = boardService.getList(page);
        model.addAttribute("bList", responseDTOS);

        // 페이징 알고리즘 작동
        PageMaker maker = new PageMaker(page, boardService.getCount(page));
        model.addAttribute("maker", maker);
        model.addAttribute("s", page);

        return "chap05/list";
    }

    // 글쓰기 화면 조회 요청
    @GetMapping("/write")
    public String write(HttpSession session) {

        if (!LoginUtil.isLogin(session)) return "redirect:/members/sign-in";

        System.out.println("/board/write : GET");
        return "chap05/write";
    }

    // 글 등록 요청 처리
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO dto, HttpSession session) {
        System.out.println("/board/write : POST");
        boardService.register(dto, session);
        return "redirect:/board/list";
    }

    // 글 삭제 요청 처리
    @GetMapping("/delete")
    public String delete(int bno) {
        System.out.println("/board/delete : GET");
        boardService.delete(bno);
        return "redirect:/board/list";
    }

    // 글 상세 조회 요청
    @GetMapping("/detail")
    public String detail(int bno, Model model, @ModelAttribute("s") Search search) {
        System.out.println("/board/detail : GET");
        model.addAttribute("b", boardService.getDetail(bno));

        return "chap05/detail";
    }

    // 글 수정 페이지 요청
    @GetMapping("/modify")
    public String modify(int bno, Model model) {
        System.out.println("/board/modify : GET");

        model.addAttribute("board", boardService.getModify(bno));

        return "chap05/modify";
    }

    // 글 수정 요청
    @PostMapping("/modify")
    public String modify(BoardModifyDTO dto) {
        System.out.println("/board/modify : POST");

        boardService.modify(dto);

        return "redirect:/board/list";
    }

    // 정렬 요청
    @GetMapping("/sort")
    public String sort(String way, Model model, Search page) {

        System.out.println("/board/sort : GET");

        List<BoardListResponseDTO> responseDTOS = boardService.getList(way, page);

        model.addAttribute("bList", responseDTOS);

        return "chap05/list";
    }

    // 검색 요청
    @PostMapping("/search")
    public String search(String keyword, Model model, Search page) {
        System.out.println("/board/search : POST");

        List<BoardListResponseDTO> responseDTOS = boardService.getListByKeyword(keyword, page);

        model.addAttribute("bList", responseDTOS);

        return "chap05/list";
    }
}
