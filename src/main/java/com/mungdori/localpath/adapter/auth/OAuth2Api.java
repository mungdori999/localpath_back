package com.mungdori.localpath.adapter.auth;

import com.mungdori.localpath.adapter.auth.dto.MemberOauth2Response;
import com.mungdori.localpath.application.auth.TokenService;
import com.mungdori.localpath.application.auth.KakaoService;
import com.mungdori.localpath.application.member.MemberService;
import com.mungdori.localpath.domain.auth.LoginOAuth2;
import com.mungdori.localpath.domain.auth.LoginRequest;
import com.mungdori.localpath.domain.auth.Token;
import com.mungdori.localpath.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuth2Api {

    private final KakaoService kakaoService;
    private final TokenService tokenService;
    private final MemberService memberService;

    @PostMapping("/kakao/member")
    public ResponseEntity<MemberOauth2Response> authUserKaKao(
            @RequestBody LoginRequest loginRequest
    ) {
        LoginOAuth2 loginOAuth2 = kakaoService.getInfo(loginRequest.accessToken());

        Member member = memberService.register(loginOAuth2);

        Token token = tokenService.createToken(member.getId(), member.getName());

        tokenService.save(token.refreshToken());

        return ResponseEntity.ok()
                .header("Authorization", token.accessToken())
                .body(new MemberOauth2Response(
                        member.getId(),
                        member.getName(),
                        token.refreshToken()));
    }

}
