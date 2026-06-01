package com.mungdori.localpath.domain.passes;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "spot_curations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotCuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String spotName;

    private String ownerTitle;

    @Column(length = 500)
    private String signatureMenu;

    @Column(length = 200)
    private String recommendedVisitTime;

    @Column(length = 1000)
    private String hiddenTip;

    public static SpotCuration create(
            String spotName,
            String ownerTitle,
            String signatureMenu,
            String recommendedVisitTime,
            String hiddenTip
    ) {
        SpotCuration curation = new SpotCuration();
        curation.spotName = requireNonNull(spotName);
        curation.ownerTitle = requireNonNull(ownerTitle);
        curation.signatureMenu = requireNonNull(signatureMenu);
        curation.recommendedVisitTime = requireNonNull(recommendedVisitTime);
        curation.hiddenTip = requireNonNull(hiddenTip);
        return curation;
    }
}
