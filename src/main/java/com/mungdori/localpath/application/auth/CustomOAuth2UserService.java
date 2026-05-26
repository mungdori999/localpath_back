package com.mungdori.localpath.application.auth;

import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.domain.auth.CustomOAuth2User;
import com.mungdori.localpath.domain.auth.KakaoResponse;
import com.mungdori.localpath.domain.auth.OAuth2Response;
import com.mungdori.localpath.domain.auth.OAuth2ResponseUser;
import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.member.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }

        Optional<Member> member = memberRepository.findByEmail(oAuth2Response.getEmail());

        if(member.isPresent()){
            OAuth2ResponseUser user
                    = new OAuth2ResponseUser("ROLE_USER", oAuth2Response.getEmail(), oAuth2Response.getName());

        return new CustomOAuth2User(user);
        }
        else {
            Member register
                    = Member.register(new MemberRegisterRequest(oAuth2Response.getEmail(), oAuth2Response.getName()));
            memberRepository.save(register);

            OAuth2ResponseUser user
                    = new OAuth2ResponseUser("ROLE_USER",register.getEmail(), register.getName());
        return new CustomOAuth2User(user);
        }
    }
}