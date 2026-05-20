package com.mungdori.localpath.adapter.auth;

import com.mungdori.localpath.application.auth.JWTService;
import com.mungdori.localpath.application.auth.KakaoService;
import com.mungdori.localpath.application.member.MemberService;
import com.mungdori.localpath.domain.auth.LoginOAuth2;
import com.mungdori.localpath.domain.auth.Token;
import com.mungdori.localpath.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuth2Api {

    private final KakaoService kakaoService;
    private final JWTService jwtService;
    private final MemberService memberService;

    @PostMapping("/kakao/member")
    public ResponseEntity<UserOauth2Response> authUserKaKao(@RequestParam String accessToken) {
        LoginOAuth2 loginOAuth2 = kakaoService.getInfo(accessToken);
        Member member = memberService.getOrCreateUser(loginOAuth2);
        Token token = jwtService.createToken(member.getId(), member.getName());
        refreshRepository.save(token.getRefreshToken());
        return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                .body(UserOauth2Response.from(user, token.getRefreshToken()));

    }

}
