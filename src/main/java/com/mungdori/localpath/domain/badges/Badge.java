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

    /** 배지 아이콘 이미지 경로 (예: /badges/welcome-member.png) */
    @Column(length = 128)
    private String image;

    private String region;
    private int orderIndex;

    /** 방문 인증으로 해금 (가입 축하 배지 등은 false) */
    private boolean visitBased;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BadgeRequirement> requirements = new ArrayList<>();

    public static Badge create(
            String badgeKey,
            String name,
            String description,
            String emoji,
            String image,
            String region,
            int orderIndex,
            boolean visitBased
    ) {
        Badge badge = new Badge();
        badge.badgeKey = requireNonNull(badgeKey);
        badge.name = requireNonNull(name);
        badge.description = requireNonNull(description);
        badge.emoji = emoji;
        badge.image = image;
        badge.region = requireNonNull(region);
        badge.orderIndex = orderIndex;
        badge.visitBased = visitBased;
        return badge;
    }

    public void addRequirement(String spotName) {
        BadgeRequirement requirement = BadgeRequirement.create(this, spotName);
        requirements.add(requirement);
    }

    public void updateVisitMetadata(
            String name,
            String description,
            String emoji,
            String image,
            String region,
            int orderIndex
    ) {
        this.name = requireNonNull(name);
        this.description = requireNonNull(description);
        this.emoji = emoji;
        this.image = image;
        this.region = requireNonNull(region);
        this.orderIndex = orderIndex;
        this.visitBased = true;
    }

    public void updateWelcomeMetadata(
            String name,
            String description,
            String image,
            String region,
            int orderIndex
    ) {
        this.name = requireNonNull(name);
        this.description = requireNonNull(description);
        this.emoji = null;
        this.image = image;
        this.region = requireNonNull(region);
        this.orderIndex = orderIndex;
        this.visitBased = false;
    }

    public void replaceRequirements(List<String> spotNames) {
        requirements.clear();
        for (String spotName : spotNames) {
            addRequirement(spotName);
        }
    }
}
