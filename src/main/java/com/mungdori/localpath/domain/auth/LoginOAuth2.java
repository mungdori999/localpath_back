package com.mungdori.localpath.domain.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record LoginOAuth2(

        @NotNull
        String name,
        @Email
        String email) {

}
