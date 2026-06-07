package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.adapter.badges.dto.BadgeRequirementResponse;
import com.mungdori.localpath.adapter.badges.dto.BadgeResponse;
import com.mungdori.localpath.adapter.badges.dto.BadgeVisitProgressResponse;
import com.mungdori.localpath.application.badges.required.BadgeRepository;
import com.mungdori.localpath.application.badges.required.MemberBadgeRepository;
import com.mungdori.localpath.application.badges.required.SpotVisitRepository;
import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.common.time.KoreaTime;
import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.MemberBadge;
import com.mungdori.localpath.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final MemberRepository memberRepository;
    private final VisitProgressEvaluator visitProgressEvaluator;

    @Transactional(readOnly = true)
    public List<BadgeResponse> getBadges(String memberEmail) {
        Member member = findMember(memberEmail);
        Set<String> visitedSpotNames = spotVisitRepository.findByMember(member).stream()
                .map(v -> v.getSpotName())
                .collect(Collectors.toSet());
        Map<Long, LocalDateTime> unlockedAtByBadgeId = memberBadgeRepository.findByMember(member).stream()
                .collect(Collectors.toMap(mb -> mb.getBadge().getId(), MemberBadge::getUnlockedAt));

        return badgeRepository.findAllWithRequirements().stream()
                .filter(badge -> !BadgeSeedData.DEPRECATED_EXPLORER_KEY.equals(badge.getBadgeKey()))
                .map(badge -> toResponse(badge, visitedSpotNames, unlockedAtByBadgeId))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<com.mungdori.localpath.adapter.badges.dto.VisitResponse> getVisits(String memberEmail) {
        Member member = findMember(memberEmail);
        return spotVisitRepository.findByMember(member).stream()
                .map(v -> new com.mungdori.localpath.adapter.badges.dto.VisitResponse(
                        v.getSpotName(),
                        KoreaTime.toOffset(v.getVerifiedAt())
                ))
                .toList();
    }

    private BadgeResponse toResponse(
            Badge badge,
            Set<String> visitedSpotNames,
            Map<Long, LocalDateTime> unlockedAtByBadgeId
    ) {
        boolean unlocked = unlockedAtByBadgeId.containsKey(badge.getId());
        BadgeVisitProgressResponse visitProgress = null;
        List<BadgeRequirementResponse> requirements;

        if (badge.isVisitBased()) {
            visitProgress = visitProgressEvaluator.progressForBadge(badge, visitedSpotNames);
            requirements = badge.getRequirements().stream()
                    .map(req -> new BadgeRequirementResponse(
                            req.getSpotName(),
                            visitProgressEvaluator.categoryForSpotName(req.getSpotName()),
                            visitedSpotNames.contains(req.getSpotName())
                    ))
                    .toList();
        } else {
            requirements = badge.getRequirements().stream()
                    .map(req -> new BadgeRequirementResponse(
                            req.getSpotName(),
                            null,
                            visitedSpotNames.contains(req.getSpotName())
                    ))
                    .toList();
        }

        int completedCount = (int) requirements.stream().filter(BadgeRequirementResponse::completed).count();
        int totalCount = requirements.size();

        return new BadgeResponse(
                badge.getBadgeKey(),
                badge.getName(),
                badge.getDescription(),
                badge.getEmoji(),
                badge.getImage(),
                badge.getRegion(),
                unlocked,
                KoreaTime.toOffset(unlockedAtByBadgeId.get(badge.getId())),
                requirements,
                completedCount,
                totalCount,
                visitProgress
        );
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MEMBER_NOT_FOUND));
    }
}
