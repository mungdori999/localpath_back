package com.mungdori.localpath.domain.survey;

import com.mungdori.localpath.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_surveys")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", unique = true, nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answersJson;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String scoresJson;

    @Column(nullable = false, length = 32)
    private String primaryType;

    @Column(nullable = false, length = 64)
    private String recommendedPassId;

    @Column(nullable = false, length = 32)
    private String recommendedCourseKey;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    public static UserSurveyEntity create(
            Member member,
            String answersJson,
            String scoresJson,
            TravelType primaryType,
            String recommendedPassId,
            String recommendedCourseKey
    ) {
        UserSurveyEntity survey = new UserSurveyEntity();
        survey.member = member;
        survey.answersJson = answersJson;
        survey.scoresJson = scoresJson;
        survey.primaryType = primaryType.name();
        survey.recommendedPassId = recommendedPassId;
        survey.recommendedCourseKey = recommendedCourseKey;
        survey.completedAt = LocalDateTime.now();
        return survey;
    }

    public void update(
            String answersJson,
            String scoresJson,
            TravelType primaryType,
            String recommendedPassId,
            String recommendedCourseKey
    ) {
        this.answersJson = answersJson;
        this.scoresJson = scoresJson;
        this.primaryType = primaryType.name();
        this.recommendedPassId = recommendedPassId;
        this.recommendedCourseKey = recommendedCourseKey;
        this.completedAt = LocalDateTime.now();
    }
}
