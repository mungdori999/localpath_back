package com.mungdori.localpath.domain.badges;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "badge_requirements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    private String spotName;

    public static BadgeRequirement create(Badge badge, String spotName) {
        BadgeRequirement requirement = new BadgeRequirement();
        requirement.badge = requireNonNull(badge);
        requirement.spotName = requireNonNull(spotName);
        return requirement;
    }
}
