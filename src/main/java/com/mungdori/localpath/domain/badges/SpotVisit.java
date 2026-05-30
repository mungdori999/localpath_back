package com.mungdori.localpath.domain.badges;

import com.mungdori.localpath.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

@Entity
@Table(
        name = "spot_visits",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "spot_name"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String spotName;
    private LocalDateTime verifiedAt;

    public static SpotVisit create(Member member, String spotName) {
        SpotVisit visit = new SpotVisit();
        visit.member = requireNonNull(member);
        visit.spotName = requireNonNull(spotName);
        visit.verifiedAt = LocalDateTime.now();
        return visit;
    }
}
