package com.mungdori.localpath.application.auth;

import com.mungdori.localpath.application.auth.provided.Oauth2Service;
import com.mungdori.localpath.domain.auth.LoginOAuth2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KakaoService implements Oauth2Service {

    @Override
    public LoginOAuth2 getInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        try {
            ResponseEntity<Map> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map.class);

            Map<String, Object> body = response.getBody();
            if (body == null) return null;

            // Parse email and name
            Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            String email = (String) kakaoAccount.get("email");
            String name = profile != null ? (String) profile.get("nickname") : null;

            return new LoginOAuth2(name, email);
        } catch (Exception e) {
            throw new IllegalArgumentException("KAKAO ACCESS ERROR");
        }
    }
}
