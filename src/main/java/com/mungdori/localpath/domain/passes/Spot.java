package com.mungdori.localpath.domain.passes;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "course_spots")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String address;
    private double lat;
    private double lng;
    private String note;
    private int orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public static Spot create(
            String name,
            String category,
            String address,
            double lat,
            double lng,
            String note,
            int orderIndex
    ) {
        Spot spot = new Spot();
        spot.name = requireNonNull(name);
        spot.category = requireNonNull(category);
        spot.address = requireNonNull(address);
        spot.lat = lat;
        spot.lng = lng;
        spot.note = requireNonNull(note);
        spot.orderIndex = orderIndex;
        return spot;
    }

    void attachTo(Course course) {
        this.course = course;
    }
}

