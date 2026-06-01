package com.mungdori.localpath.adapter.passes.dto;

import java.time.LocalDateTime;

public record MemberPassTicketResponse(
        String ticketId,
        String passId,
        String passName,
        String passImage,
        int unitPrice,
        LocalDateTime purchasedAt,
        LocalDateTime expiresAt,
        boolean valid
) {
}
