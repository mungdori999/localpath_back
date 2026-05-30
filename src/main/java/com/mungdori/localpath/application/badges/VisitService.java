package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.adapter.badges.dto.VerifyVisitResponse;
import com.mungdori.localpath.application.badges.required.BadgeRepository;
import com.mungdori.localpath.application.badges.required.MemberBadgeRepository;
import com.mungdori.localpath.application.badges.required.SpotVisitRepository;
import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.application.passes.required.SpotRepository;
import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.MemberBadge;
import com.mungdori.localpath.domain.badges.SpotVisit;
import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.passes.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final SpotRepository spotRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public VerifyVisitResponse verifyVisit(String memberEmail, String spotName, double lat, double lng) {
        if (spotName == null || spotName.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SPOT_NAME_REQUIRED);
        }

        Member member = findMember(memberEmail);
        Spot spot = spotRepository.findFirstByName(spotName.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.SPOT_NOT_FOUND));

        if (!LocationVerifier.isWithinRange(spot.getLat(), spot.getLng(), lat, lng)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.VISIT_TOO_FAR);
        }

        if (spotVisitRepository.existsByMemberAndSpotName(member, spot.getName())) {
            LocalDateTime verifiedAt = spotVisitRepository
                    .findByMemberAndSpotName(member, spot.getName())
                    .map(SpotVisit::getVerifiedAt)
                    .orElse(LocalDateTime.now());
            return new VerifyVisitResponse(spot.getName(), verifiedAt, true, List.of());
        }

        SpotVisit visit = SpotVisit.create(member, spot.getName());
        spotVisitRepository.save(visit);

        List<String> newlyUnlocked = unlockEligibleBadges(member);

        return new VerifyVisitResponse(
                spot.getName(),
                visit.getVerifiedAt(),
                false,
                newlyUnlocked
        );
    }

    private List<String> unlockEligibleBadges(Member member) {
        Set<String> visitedSpotNames = spotVisitRepository.findByMember(member).stream()
                .map(SpotVisit::getSpotName)
                .collect(Collectors.toSet());

        List<Badge> badges = badgeRepository.findAllWithRequirements();
        List<String> newlyUnlocked = new ArrayList<>();

        for (Badge badge : badges) {
            if (memberBadgeRepository.existsByMemberAndBadge(member, badge)) {
                continue;
            }

            boolean allMet = badge.getRequirements().stream()
                    .allMatch(req -> visitedSpotNames.contains(req.getSpotName()));

            if (allMet) {
                memberBadgeRepository.save(MemberBadge.unlock(member, badge));
                newlyUnlocked.add(badge.getBadgeKey());
            }
        }

        return newlyUnlocked;
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MEMBER_NOT_FOUND));
    }
}
