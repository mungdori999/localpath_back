package com.mungdori.localpath.adapter.auth.dto;

import jakarta.validation.constraints.NotNull;

public record MemberOauth2Response(

        @NotNull
        Long id,
        @NotNull
        String name,
        @NotNull
        String refreshToken
) {
}
