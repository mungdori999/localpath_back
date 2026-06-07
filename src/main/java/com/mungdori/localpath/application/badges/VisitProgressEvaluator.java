package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.adapter.badges.dto.BadgeVisitProgressResponse;
import com.mungdori.localpath.application.passes.required.SpotRepository;
import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.BadgeRequirement;
import com.mungdori.localpath.domain.badges.SpotVisit;
import com.mungdori.localpath.domain.passes.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VisitProgressEvaluator {

    private final SpotRepository spotRepository;

    public boolean isBadgeUnlocked(Badge badge, Set<String> visitedSpotNames) {
        if (!badge.isVisitBased()) {
            return false;
        }
        if (badge.getRequirements().isEmpty()) {
            return false;
        }
        return badge.getRequirements().stream()
                .allMatch(req -> visitedSpotNames.contains(req.getSpotName()));
    }

    public BadgeVisitProgressResponse progressForBadge(Badge badge, Set<String> visitedSpotNames) {
        List<BadgeRequirement> requirements = badge.getRequirements();
        int completed = (int) requirements.stream()
                .filter(req -> visitedSpotNames.contains(req.getSpotName()))
                .count();

        Set<String> visitedCategories = new LinkedHashSet<>();
        Set<String> targetCategories = new LinkedHashSet<>();
        for (BadgeRequirement req : requirements) {
            targetCategories.add(categoryForSpotName(req.getSpotName()));
            if (visitedSpotNames.contains(req.getSpotName())) {
                visitedCategories.add(categoryForSpotName(req.getSpotName()));
            }
        }

        return new BadgeVisitProgressResponse(
                completed,
                requirements.size(),
                visitedCategories.size(),
                targetCategories.size(),
                List.copyOf(visitedCategories)
        );
    }

    public String categoryForSpotName(String spotName) {
        return spotRepository.findFirstByName(spotName)
                .map(Spot::getCategory)
                .map(SpotCategoryNormalizer::normalize)
                .orElse("기타");
    }

    public String resolveCategory(SpotVisit visit) {
        if (visit.getSpotCategory() != null && !visit.getSpotCategory().isBlank()) {
            return SpotCategoryNormalizer.normalize(visit.getSpotCategory());
        }
        return categoryForSpotName(visit.getSpotName());
    }

    public Set<String> distinctCategories(List<SpotVisit> visits) {
        Set<String> categories = new LinkedHashSet<>();
        for (SpotVisit visit : visits) {
            categories.add(resolveCategory(visit));
        }
        return categories;
    }
}
