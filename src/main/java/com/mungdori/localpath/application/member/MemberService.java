package com.mungdori.localpath.application.member;

import com.mungdori.localpath.application.member.provided.MemberRegister;
import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.domain.auth.LoginOAuth2;
import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.member.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;

    @Override
    public Member register(LoginOAuth2 registerRequest) {
        return memberRepository.findByEmail(registerRequest.email())
                .orElseGet(() ->
                        memberRepository.save(Member.register(
                                new MemberRegisterRequest(registerRequest.email(), registerRequest.name())
                        ))
                );
    }
}
