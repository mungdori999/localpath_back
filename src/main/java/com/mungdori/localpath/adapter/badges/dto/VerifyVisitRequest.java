package com.mungdori.localpath.adapter.badges.dto;

public record VerifyVisitRequest(
        String spotName,
        double lat,
        double lng
) {
}
