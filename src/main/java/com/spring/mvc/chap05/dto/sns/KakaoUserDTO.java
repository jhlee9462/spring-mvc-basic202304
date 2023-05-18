package com.spring.mvc.chap05.dto.sns;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class KakaoUserDTO {

    private long id;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Setter @Getter @ToString
    public class KakaoAccount {

        private Profile profile;
        private String email;

        @Setter @Getter @ToString
        public class Profile {

            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;

        }
    }
}
