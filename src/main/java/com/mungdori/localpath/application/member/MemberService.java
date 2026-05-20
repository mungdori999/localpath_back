package com.mungdori.localpath.application.member;

import com.mungdori.localpath.application.member.provided.MemberRegister;
import com.mungdori.localpath.domain.auth.LoginOAuth2;
import com.mungdori.localpath.domain.member.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements MemberRegister {
    @Override
    public Member register(LoginOAuth2 registerRequest) {
        return null;
    }
}
