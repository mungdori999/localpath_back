package com.mungdori.localpath.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static java.util.Objects.requireNonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;

    public static RefreshToken create(String refreshToken) {
        RefreshToken token = new RefreshToken();
        token.refreshToken = requireNonNull(refreshToken);

        return token;
    }
}
