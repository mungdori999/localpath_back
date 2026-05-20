package com.mungdori.localpath.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberRegisterRequest(
        @Email
        String email,
        @NotNull
        String name
) {

}
