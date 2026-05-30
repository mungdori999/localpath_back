package com.mungdori.localpath.domain.badges;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "badges")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String badgeKey;

    private String name;
    private String description;
    private String emoji;
    private String region;
    private int orderIndex;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BadgeRequirement> requirements = new ArrayList<>();

    public static Badge create(
            String badgeKey,
            String name,
            String description,
            String emoji,
            String region,
            int orderIndex
    ) {
        Badge badge = new Badge();
        badge.badgeKey = requireNonNull(badgeKey);
        badge.name = requireNonNull(name);
        badge.description = requireNonNull(description);
        badge.emoji = requireNonNull(emoji);
        badge.region = requireNonNull(region);
        badge.orderIndex = orderIndex;
        return badge;
    }

    public void addRequirement(String spotName) {
        BadgeRequirement requirement = BadgeRequirement.create(this, spotName);
        requirements.add(requirement);
    }
}
