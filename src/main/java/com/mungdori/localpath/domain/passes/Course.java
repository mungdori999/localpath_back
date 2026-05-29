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
public class Course {

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
    private Pass pass;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private final List<Spot> spots = new ArrayList<>();

    public static Course create(
            String courseKey,
            String name,
            String emoji,
            String description,
            int orderIndex
    ) {
        Course course = new Course();
        course.courseKey = requireNonNull(courseKey);
        course.name = requireNonNull(name);
        course.emoji = requireNonNull(emoji);
        course.description = requireNonNull(description);
        course.orderIndex = orderIndex;
        return course;
    }

    void attachTo(Pass pass) {
        this.pass = pass;
    }

    public void addSpot(Spot spot) {
        spot.attachTo(this);
        this.spots.add(spot);
    }
}

