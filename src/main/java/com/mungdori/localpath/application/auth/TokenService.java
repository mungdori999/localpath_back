package com.mungdori.localpath.application.auth;

import com.mungdori.localpath.application.auth.required.TokenRepository;
import com.mungdori.localpath.domain.auth.RefreshToken;
import com.mungdori.localpath.domain.auth.Token;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
@Transactional
public class TokenService {

    private final SecretKey secretKey;
    private final TokenRepository tokenRepository;

    @Value("${spring.jwt.accessToken-expire-length}")
    private long accessExpireLong;
    @Value("${spring.jwt.refresh-expire-length}")
    private long refreshExpireLong;


    public TokenService(@Value("${spring.jwt.secret}") String secret, TokenRepository tokenRepository) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.tokenRepository = tokenRepository;
    }

    public Long getId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }

    public String getName(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("name", String.class);
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }


    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public RefreshToken save(String refreshToken) {
        return tokenRepository.save(RefreshToken.create(refreshToken));
    }

    public Token createToken(Long id, String name) {
        String accessToken = Jwts.builder()
                .claim("id", id)
                .claim("name", name)
                .claim("category", "accessToken")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpireLong))
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("id", id)
                .claim("name", name)
                .claim("category", "refreshToken")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpireLong))
                .signWith(secretKey)
                .compact();
        return new Token(accessToken, refreshToken);
    }
}
