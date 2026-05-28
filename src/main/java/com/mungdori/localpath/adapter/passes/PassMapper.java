package com.mungdori.localpath.adapter.passes;

import com.mungdori.localpath.adapter.passes.dto.CourseResponse;
import com.mungdori.localpath.adapter.passes.dto.PassResponse;
import com.mungdori.localpath.adapter.passes.dto.SpotResponse;
import com.mungdori.localpath.domain.passes.CourseEntity;
import com.mungdori.localpath.domain.passes.PassEntity;
import com.mungdori.localpath.domain.passes.SpotEntity;

import java.util.List;

public class PassMapper {
    private PassMapper() {
    }

    public static PassResponse toResponse(PassEntity pass) {
        List<CourseResponse> courses = pass.getCourses().stream()
                .map(PassMapper::toResponse)
                .toList();

        return new PassResponse(
                pass.getId(),
                pass.getName(),
                pass.getTagline(),
                pass.getDuration(),
                pass.getPrice(),
                pass.getDescription(),
                pass.getImage(),
                courses
        );
    }

    private static CourseResponse toResponse(CourseEntity course) {
        List<SpotResponse> spots = course.getSpots().stream()
                .map(PassMapper::toResponse)
                .toList();

        return new CourseResponse(
                course.getCourseKey(),
                course.getName(),
                course.getEmoji(),
                course.getDescription(),
                spots
        );
    }

    private static SpotResponse toResponse(SpotEntity spot) {
        return new SpotResponse(
                spot.getName(),
                spot.getCategory(),
                spot.getAddress(),
                spot.getLat(),
                spot.getLng(),
                spot.getNote()
        );
    }
}

