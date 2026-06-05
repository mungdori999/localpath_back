package com.mungdori.localpath.domain.passes;

import com.mungdori.localpath.common.time.KoreaTime;
import com.mungdori.localpath.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "member_pass_tickets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPassTicket {

    private static final int VALID_HOURS = 24;

    @Id
    @Column(length = 36)
    private String ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_id", nullable = false)
    private Pass pass;

    private int unitPrice;

    private LocalDateTime purchasedAt;

    private LocalDateTime expiresAt;

    /** route1 | route2 | route3 — 결제 금액 사용 비율(데모 표시용) */
    @Column(length = 16)
    private String spendingFocus;

    public static MemberPassTicket purchase(
            Member member,
            Pass pass,
            LocalDateTime purchasedAt,
            String spendingFocus
    ) {
        MemberPassTicket ticket = new MemberPassTicket();
        ticket.ticketId = UUID.randomUUID().toString();
        ticket.member = requireNonNull(member);
        ticket.pass = requireNonNull(pass);
        ticket.unitPrice = pass.getPrice();
        ticket.purchasedAt = requireNonNull(purchasedAt);
        ticket.expiresAt = purchasedAt.plusHours(VALID_HOURS);
        ticket.spendingFocus = requireNonNull(spendingFocus);
        return ticket;
    }

    public boolean isValid() {
        return KoreaTime.nowLocal().isBefore(expiresAt);
    }
}
