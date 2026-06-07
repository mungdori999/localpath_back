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
            badgeRepository.findByBadgeKey(seedBadge.getBadgeKey())
                    .ifPresentOrElse(
                            existing -> syncBadge(existing, seedBadge),
                            () -> badgeRepository.save(seedBadge)
                    );
        }
    }

    private void syncBadge(Badge existing, Badge seedBadge) {
        if (seedBadge.isVisitBased()) {
            existing.updateVisitMetadata(
                    seedBadge.getName(),
                    seedBadge.getDescription(),
                    seedBadge.getEmoji(),
                    seedBadge.getImage(),
                    seedBadge.getRegion(),
                    seedBadge.getOrderIndex()
            );
            existing.replaceRequirements(
                    seedBadge.getRequirements().stream()
                            .map(req -> req.getSpotName())
                            .toList()
            );
            return;
        }

        existing.updateWelcomeMetadata(
                seedBadge.getName(),
                seedBadge.getDescription(),
                seedBadge.getImage(),
                seedBadge.getRegion(),
                seedBadge.getOrderIndex()
        );
    }
}
