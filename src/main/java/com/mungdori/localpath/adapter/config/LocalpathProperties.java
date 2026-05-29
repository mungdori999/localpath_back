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
}
