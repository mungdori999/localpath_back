package com.mungdori.localpath.application.passes.required;

import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.passes.MemberPassTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberPassTicketRepository extends JpaRepository<MemberPassTicket, String> {

    @Query("""
            SELECT t FROM MemberPassTicket t
            JOIN FETCH t.pass
            WHERE t.member = :member
            ORDER BY t.purchasedAt DESC
            """)
    List<MemberPassTicket> findByMemberWithPass(@Param("member") Member member);

    @Query("""
            SELECT t FROM MemberPassTicket t
            JOIN FETCH t.pass
            WHERE t.ticketId = :ticketId AND t.member = :member
            """)
    Optional<MemberPassTicket> findByTicketIdAndMember(
            @Param("ticketId") String ticketId,
            @Param("member") Member member
    );
}
