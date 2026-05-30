package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.application.badges.required.BadgeRepository;
import com.mungdori.localpath.domain.badges.Badge;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(2)
@RequiredArgsConstructor
public class BadgeDataSeeder implements ApplicationRunner {

    private final BadgeRepository badgeRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (Badge seedBadge : BadgeSeedData.badges()) {
            if (badgeRepository.findByBadgeKey(seedBadge.getBadgeKey()).isPresent()) {
                continue;
            }
            badgeRepository.save(seedBadge);
        }
    }
}
