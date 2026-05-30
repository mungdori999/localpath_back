package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.adapter.badges.dto.BadgeRequirementResponse;
import com.mungdori.localpath.adapter.badges.dto.BadgeResponse;
import com.mungdori.localpath.application.badges.required.BadgeRepository;
import com.mungdori.localpath.application.badges.required.MemberBadgeRepository;
import com.mungdori.localpath.application.badges.required.SpotVisitRepository;
import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.MemberBadge;
import com.mungdori.localpath.domain.badges.SpotVisit;
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

    @Transactional(readOnly = true)
    public List<BadgeResponse> getBadges(String memberEmail) {
        Member member = findMember(memberEmail);
        List<Badge> badges = badgeRepository.findAllWithRequirements();
        Set<String> visitedSpotNames = spotVisitRepository.findByMember(member).stream()
                .map(SpotVisit::getSpotName)
                .collect(Collectors.toSet());
        Map<Long, LocalDateTime> unlockedAtByBadgeId = memberBadgeRepository.findByMember(member).stream()
                .collect(Collectors.toMap(mb -> mb.getBadge().getId(), MemberBadge::getUnlockedAt));

        return badges.stream()
                .map(badge -> toResponse(badge, visitedSpotNames, unlockedAtByBadgeId))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<com.mungdori.localpath.adapter.badges.dto.VisitResponse> getVisits(String memberEmail) {
        Member member = findMember(memberEmail);
        return spotVisitRepository.findByMember(member).stream()
                .map(v -> new com.mungdori.localpath.adapter.badges.dto.VisitResponse(
                        v.getSpotName(),
                        v.getVerifiedAt()
                ))
                .toList();
    }

    private BadgeResponse toResponse(
            Badge badge,
            Set<String> visitedSpotNames,
            Map<Long, LocalDateTime> unlockedAtByBadgeId
    ) {
        List<BadgeRequirementResponse> requirements = badge.getRequirements().stream()
                .map(req -> new BadgeRequirementResponse(
                        req.getSpotName(),
                        visitedSpotNames.contains(req.getSpotName())
                ))
                .toList();

        int completedCount = (int) requirements.stream().filter(BadgeRequirementResponse::completed).count();
        int totalCount = requirements.size();
        boolean unlocked = unlockedAtByBadgeId.containsKey(badge.getId());

        return new BadgeResponse(
                badge.getBadgeKey(),
                badge.getName(),
                badge.getDescription(),
                badge.getEmoji(),
                badge.getRegion(),
                unlocked,
                unlockedAtByBadgeId.get(badge.getId()),
                requirements,
                completedCount,
                totalCount
        );
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MEMBER_NOT_FOUND));
    }
}
