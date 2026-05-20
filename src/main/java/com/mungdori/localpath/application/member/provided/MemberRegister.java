package com.mungdori.localpath.application.member.provided;


import com.mungdori.localpath.domain.auth.LoginOAuth2;
import com.mungdori.localpath.domain.member.Member;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 */
public interface MemberRegister {
    Member register(@Valid LoginOAuth2 registerRequest);

}
