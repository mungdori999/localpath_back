package com.mungdori.localpath.adapter.badges;

import com.mungdori.localpath.adapter.AuthorizationUtil;
import com.mungdori.localpath.adapter.badges.dto.VerifyVisitRequest;
import com.mungdori.localpath.adapter.badges.dto.VerifyVisitResponse;
import com.mungdori.localpath.adapter.badges.dto.VisitResponse;
import com.mungdori.localpath.application.badges.BadgeService;
import com.mungdori.localpath.application.badges.VisitService;
import com.mungdori.localpath.common.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.VISITS)
public class VisitApi {

    private final VisitService visitService;
    private final BadgeService badgeService;

    @GetMapping("/me")
    public List<VisitResponse> getMyVisits() {
        return badgeService.getVisits(AuthorizationUtil.requireEmail());
    }

    @PostMapping
    public VerifyVisitResponse verifyVisit(@RequestBody VerifyVisitRequest request) {
        return visitService.verifyVisit(
                AuthorizationUtil.requireEmail(),
                request.spotName(),
                request.lat(),
                request.lng()
        );
    }
}
