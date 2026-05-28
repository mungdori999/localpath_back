package com.mungdori.localpath.adapter.passes.dto;

import java.util.List;

public record PassResponse(
        String id,
        String name,
        String tagline,
        String duration,
        int price,
        String description,
        String image,
        List<CourseResponse> courses
) {
}

