package com.mungdori.localpath.adapter.passes.dto;

import java.time.OffsetDateTime;

public record MemberPassTicketResponse(
        String ticketId,
        String passId,
        String passName,
        String passImage,
        int unitPrice,
        OffsetDateTime purchasedAt,
        OffsetDateTime expiresAt,
        boolean valid
) {
}
