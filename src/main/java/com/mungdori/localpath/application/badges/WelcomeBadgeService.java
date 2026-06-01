package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.application.badges.required.BadgeRepository;
import com.mungdori.localpath.application.badges.required.MemberBadgeRepository;
import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.MemberBadge;
import com.mungdori.localpath.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WelcomeBadgeService {

    public static final String WELCOME_BADGE_KEY = "welcome-member";

    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;

    @Transactional
    public void grantWelcomeBadge(Member member) {
        Badge badge = badgeRepository.findByBadgeKey(WELCOME_BADGE_KEY).orElse(null);
        if (badge == null) {
            return;
        }
        if (memberBadgeRepository.existsByMemberAndBadge(member, badge)) {
            return;
        }
        memberBadgeRepository.save(MemberBadge.unlock(member, badge));
    }
}
