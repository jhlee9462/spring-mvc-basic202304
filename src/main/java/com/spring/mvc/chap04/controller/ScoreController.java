package com.spring.mvc.chap04.controller;

import com.spring.mvc.chap04.dto.ScoreListResponseDTO;
import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap04.repository.ScoreRepositoryImpl;
import com.spring.mvc.chap04.service.ScoreService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    # 요청 URL
    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
    - /score/list : GET

    2. 성적 정보 등록 처리 요청
    - /score/register : POST

    3. 성적정보 삭제 요청
    - /score/remove : POST

    4. 성적정보 상세 조회 요청
    - /score/detail : GET
 */
@Controller
@RequestMapping("/score")
//@AllArgsConstructor : 모든 필드를 초기화하는 생성자
@RequiredArgsConstructor // : final필드만 초기화하는 생성자
public class ScoreController {

    // 저장소에 의존해야 데이터를 받아서 클라이언트에게 응답할 수 있음
//    private final ScoreRepository repository;
    private final ScoreService scoreService;

    // 만약에 클래스의 생성자가 단 1개라면
    // 자동으로 @Autowired를 써줌

//    @Autowired
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    // 1. 성적등록화면 띄우기 + 정보목록조회
    @GetMapping("/list")
    public String list(Model model,
                      @RequestParam(defaultValue = "num") String sort) {
        System.out.println("/score/list : GET!");
        System.out.println("정렬 요구사항: " + sort);

        List<ScoreListResponseDTO> responseDTOList
                = scoreService.getList(sort);

//        List<ScoreListResponseDTO> responseDTOList = new ArrayList<>();
//        for (Score s : scoreList) {
//            ScoreListResponseDTO dto = new ScoreListResponseDTO(s);
//            responseDTOList.add(dto);
//        }

        model.addAttribute("sList", responseDTOList);

        return "chap04/score-list";
    }
    // 2. 성적 정보 등록 처리 요청
    @PostMapping("/register")
    public String register(ScoreRequestDTO dto) {

        // 입력데이터(쿼리스트링) 읽기
        System.out.println("/score/register : POST! - " + dto);

        scoreService.insertScore(dto);

        /*
            등록요청에서 JSP 뷰 포워딩을 하면
            갱신된 목록을 다시한번 저장소에서 불러와
            모델에 담는 추가적인 코드가 필요하지만

            리다이렉트를 통해서 위에서 만든 /score/list :GET
            을 자동으로 다시 보낼 수 있다면 번거로운 코드가
            줄어들 수 있겠다.
         */
        return "redirect:/score/list";
    }
    // 3. 성적정보 삭제 요청
    @GetMapping("/remove")
    public String remove(int stuNum) {
        System.out.println("/score/remove : GET!");

        scoreService.delete(stuNum);

        return "redirect:/score/list";
    }
    // 4. 성적정보 상세 조회 요청
    @GetMapping("/detail")
    public String detail(int stuNum, Model model) {
        System.out.println("/score/detail : GET!");
        retrieve(stuNum, model);
        return "chap04/score-detail";
    }


    // 5. 수정 화면 열어주기
    @GetMapping("/modify")
    public String modify(int stuNum, Model model) {
        System.out.println("/score/modify : GET!");
        retrieve(stuNum, model);
        return "chap04/score-modify";
    }

    private void retrieve(int stuNum, Model model) {
        Score score = scoreService.retrieve(stuNum);
        model.addAttribute("s", score);
    }

    // 6. 수정 완료 처리하기
    @PostMapping("/modify")
    public String modify(int stuNum, ScoreRequestDTO dto) {
        System.out.println("/score/modify : POST!");

        Score score = scoreService.retrieve(stuNum);
        score.changeScore(dto);
        scoreService.modify(score);

        return "redirect:/score/detail?stuNum=" + stuNum; // 상세보기페이지로 리다이렉트
    }



}
