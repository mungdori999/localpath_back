package com.mungdori.localpath.adapter.config;

import com.mungdori.localpath.common.constants.AuthConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "localpath")
public class LocalpathProperties {

    private Frontend frontend = new Frontend();
    private Cors cors = new Cors();
    private Auth auth = new Auth();

    @Getter
    @Setter
    public static class Frontend {

        private String url = "http://localhost:5173";

        public String oauthRedirectUrl(String accessToken) {
            return url + "?" + AuthConstants.OAUTH_ACCESS_QUERY_PARAM + "=" + accessToken;
        }
    }

    @Getter
    @Setter
    public static class Cors {
        private List<String> allowedOrigins = new ArrayList<>(List.of("http://localhost:5173"));
    }

    @Getter
    @Setter
    public static class Auth {

        /** HTTPS·ALB 운영: true. 로컬 HTTP 개발: false */
        private boolean cookieSecure = true;

        /** 프론트↔API cross-origin: None (Secure=true 필요). 로컬 HTTP: Lax */
        private String cookieSameSite = "None";

        /** 선택. 서브도메인 공유 시 예: .example.com */
        private String cookieDomain;
    }
}
