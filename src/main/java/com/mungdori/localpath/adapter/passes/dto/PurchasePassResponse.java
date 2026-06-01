package com.mungdori.localpath.adapter.passes.dto;

import java.util.List;

public record PurchasePassResponse(
        List<MemberPassTicketResponse> tickets
) {
}
