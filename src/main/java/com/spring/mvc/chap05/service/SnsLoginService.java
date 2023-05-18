package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.SignupRequestDTO;
import com.spring.mvc.chap05.dto.sns.KakaoUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SnsLoginService {

    private final MemberService service;

    // 카카오 로그인 처리
    public void kakaoService(Map<String, String> map) {
        // 인가코드를 통해 토큰 발급받기
        String accessToken = getKakaoAccesstoken(map);
        log.info("access token : {} ", accessToken);

        // 토큰을 통해 사용자 정보 가져오기
        KakaoUserDTO kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 사용자 정보를 통해 우리 서비스 회원가입 진행
        service.join(
                SignupRequestDTO.builder()
                        .account(kakaoUserInfo.getKakaoAccount().getEmail())
                        .email(kakaoUserInfo.getKakaoAccount().getEmail())
                        .password("9999")
                        .name(kakaoUserInfo.getKakaoAccount().getProfile().getNickname())
                        .build(),
                kakaoUserInfo.getKakaoAccount().getProfile().getThumbnailImageUrl()
                        );
    }

    private KakaoUserDTO getKakaoUserInfo(String accessToken) {

        String requestUri = "https://kapi.kakao.com/v2/user/me";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<KakaoUserDTO> responseEntity = template.exchange(
                requestUri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                KakaoUserDTO.class
        );

        KakaoUserDTO responseMap = responseEntity.getBody();

        log.info("responseMap : {}", responseMap);

        return responseMap;
    }

    private String getKakaoAccesstoken(Map<String, String> requestMap) {

//        log.info("{}", requestMap);

        // 요청 uri
        String requestUri = "https://kauth.kakao.com/oauth/token";

        // 요청 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestMap.get("kakaoAppKey"));
        params.add("redirect_uri", requestMap.get("kakaoRequestURI"));
        params.add("code", requestMap.get("code"));

        // 카카오 서버로 post 통신
        RestTemplate template = new RestTemplate();

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, httpHeaders);

        // 통신을 보내면서 응답데이터를 리턴
        // param1 : 요청 URL
        // param2 : 요청 방식
        // param3 : 헤더와 요청파라미터 정보 엔티티
        // param4 : 응답데이터를 받을 객체의 타입 ( ex: dto, map )
        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        // 응답데이터에서 필요한 정보를 가져오기
        Map<String, Object> responseMap = (Map<String, Object>) responseEntity.getBody();
        log.info("토큰 요청 응답데이터 : {}", responseMap);

        return (String) responseMap.get("access_token");
    }

}
