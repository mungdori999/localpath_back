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
        name = "member_badges",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "badge_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    private LocalDateTime unlockedAt;

    public static MemberBadge unlock(Member member, Badge badge) {
        MemberBadge memberBadge = new MemberBadge();
        memberBadge.member = requireNonNull(member);
        memberBadge.badge = requireNonNull(badge);
        memberBadge.unlockedAt = LocalDateTime.now();
        return memberBadge;
    }
}
