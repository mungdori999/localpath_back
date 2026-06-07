package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.application.badges.required.BadgeRepository;
import com.mungdori.localpath.application.badges.required.MemberBadgeRepository;
import com.mungdori.localpath.application.badges.required.SpotVisitRepository;
import com.mungdori.localpath.application.passes.required.SpotRepository;
import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.MemberBadge;
import com.mungdori.localpath.domain.badges.SpotVisit;
import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.passes.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarterVisitService {

    public static final String ANOVE_SPOT = "어노브 ANOVE - 망리단길점";
    public static final String BLUE_BOTTLE_SPOT = "블루보틀 - 망리단길점";

    private static final List<String> STARTER_SPOTS = List.of(ANOVE_SPOT, BLUE_BOTTLE_SPOT);

    private final SpotRepository spotRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final VisitProgressEvaluator visitProgressEvaluator;

    /** 데모: 어노브·블루보틀은 가입·로그인 시 방문 인증 완료 처리 */
    @Transactional
    public void grantStarterVisits(Member member) {
        for (String spotName : STARTER_SPOTS) {
            if (spotVisitRepository.existsByMemberAndSpotName(member, spotName)) {
                continue;
            }
            spotRepository.findFirstByName(spotName).ifPresent(spot ->
                    spotVisitRepository.save(
                            SpotVisit.create(member, spot.getName(), spot.getCategory())
                    )
            );
        }
        unlockEligibleBadges(member);
    }

    private void unlockEligibleBadges(Member member) {
        Set<String> visitedSpotNames = spotVisitRepository.findByMember(member).stream()
                .map(SpotVisit::getSpotName)
                .collect(Collectors.toSet());

        for (Badge badge : badgeRepository.findAllWithRequirements()) {
            if (!badge.isVisitBased()) {
                continue;
            }
            if (BadgeSeedData.DEPRECATED_EXPLORER_KEY.equals(badge.getBadgeKey())) {
                continue;
            }
            if (memberBadgeRepository.existsByMemberAndBadge(member, badge)) {
                continue;
            }
            if (!visitProgressEvaluator.isBadgeUnlocked(badge, visitedSpotNames)) {
                continue;
            }
            memberBadgeRepository.save(MemberBadge.unlock(member, badge));
        }
    }
}
