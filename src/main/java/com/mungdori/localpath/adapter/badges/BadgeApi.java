package com.mungdori.localpath.adapter.badges;

import com.mungdori.localpath.adapter.AuthorizationUtil;
import com.mungdori.localpath.adapter.badges.dto.BadgeResponse;
import com.mungdori.localpath.application.badges.BadgeService;
import com.mungdori.localpath.common.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.BADGES)
public class BadgeApi {

    private final BadgeService badgeService;

    @GetMapping
    public List<BadgeResponse> getBadges() {
        return badgeService.getBadges(AuthorizationUtil.requireEmail());
    }
}
