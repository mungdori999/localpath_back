package com.mungdori.localpath.domain.passes;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "pass_courses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // API에서 사용하는 stable id
    private String courseKey;

    private String name;
    private String emoji;
    private String description;
    private int orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_id", nullable = false)
    private PassEntity pass;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private final List<SpotEntity> spots = new ArrayList<>();

    public static CourseEntity create(
            String courseKey,
            String name,
            String emoji,
            String description,
            int orderIndex
    ) {
        CourseEntity course = new CourseEntity();
        course.courseKey = requireNonNull(courseKey);
        course.name = requireNonNull(name);
        course.emoji = requireNonNull(emoji);
        course.description = requireNonNull(description);
        course.orderIndex = orderIndex;
        return course;
    }

    void attachTo(PassEntity pass) {
        this.pass = pass;
    }

    public void addSpot(SpotEntity spot) {
        spot.attachTo(this);
        this.spots.add(spot);
    }
}

