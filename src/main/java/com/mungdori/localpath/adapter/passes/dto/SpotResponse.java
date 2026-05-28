package com.mungdori.localpath.adapter.passes.dto;

public record SpotResponse(
        String name,
        String category,
        String address,
        double lat,
        double lng,
        String note
) {
}

