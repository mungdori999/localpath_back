package com.mungdori.localpath.adapter.passes.dto;

import java.util.List;

public record CourseResponse(
        String id,
        String name,
        String emoji,
        String description,
        List<SpotResponse> spots
) {
}

