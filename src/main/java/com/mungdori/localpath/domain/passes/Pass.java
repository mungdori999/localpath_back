package com.mungdori.localpath.domain.passes;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "passes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pass {

    @Id
    private String id;

    private String name;
    private String tagline;
    private String duration;
    private int price;
    private String description;
    private String image;

    @OneToMany(mappedBy = "pass", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private final List<Course> courses = new ArrayList<>();

    public static Pass create(
            String id,
            String name,
            String tagline,
            String duration,
            int price,
            String description,
            String image
    ) {
        Pass pass = new Pass();
        pass.id = requireNonNull(id);
        pass.name = requireNonNull(name);
        pass.tagline = requireNonNull(tagline);
        pass.duration = requireNonNull(duration);
        pass.price = price;
        pass.description = requireNonNull(description);
        pass.image = requireNonNull(image);
        return pass;
    }

    public void addCourse(Course course) {
        course.attachTo(this);
        this.courses.add(course);
    }
}

