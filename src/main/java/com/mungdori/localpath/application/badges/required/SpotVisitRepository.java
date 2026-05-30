package com.mungdori.localpath.application.badges.required;

import com.mungdori.localpath.domain.badges.SpotVisit;
import com.mungdori.localpath.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpotVisitRepository extends JpaRepository<SpotVisit, Long> {

    List<SpotVisit> findByMember(Member member);

    boolean existsByMemberAndSpotName(Member member, String spotName);

    Optional<SpotVisit> findByMemberAndSpotName(Member member, String spotName);
}
