package com.mungdori.localpath.common.constants;

public final class ApiPaths {

    public static final String API = "/api";

    public static final String AUTH_PATTERN = API + "/auth/**";
    public static final String REISSUE = API + "/reissue";
    public static final String PASSES_PATTERN = API + "/passes/**";
    public static final String PASSES = API + "/passes";
    public static final String LOGOUT = API + "/logout";
    public static final String SURVEY_PATTERN = API + "/survey/**";
    public static final String SURVEY = API + "/survey";
    public static final String BADGES_PATTERN = API + "/badges/**";
    public static final String BADGES = API + "/badges";
    public static final String VISITS_PATTERN = API + "/visits/**";
    public static final String VISITS = API + "/visits";

    private ApiPaths() {
    }
}
