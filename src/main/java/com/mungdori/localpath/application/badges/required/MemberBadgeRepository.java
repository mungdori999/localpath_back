package com.mungdori.localpath.application.badges.required;

import com.mungdori.localpath.domain.badges.Badge;
import com.mungdori.localpath.domain.badges.MemberBadge;
import com.mungdori.localpath.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    List<MemberBadge> findByMember(Member member);

    Optional<MemberBadge> findByMemberAndBadge(Member member, Badge badge);

    boolean existsByMemberAndBadge(Member member, Badge badge);
}
