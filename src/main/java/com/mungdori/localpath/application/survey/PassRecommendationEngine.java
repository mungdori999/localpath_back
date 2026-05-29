package com.mungdori.localpath.application.survey;

import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.common.constants.PassIds;
import com.mungdori.localpath.domain.passes.Course;
import com.mungdori.localpath.domain.passes.Pass;
import com.mungdori.localpath.domain.survey.TravelType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PassRecommendationEngine {

    public record Recommendation(
            String passId,
            String courseKey,
            TravelType primaryType,
            String reason
    ) {
    }

    public Recommendation recommend(Pass oneStep, Pass twoStep, EnumMap<TravelType, Integer> scores) {
        TravelType primary = scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(TravelType.HEALING);

        int adventure = scores.get(TravelType.EXPERIENCE) + scores.get(TravelType.NIGHT);
        int relaxFood = scores.get(TravelType.HEALING) + scores.get(TravelType.FOOD);

        String passId = choosePass(primary, adventure, relaxFood, twoStep);
        Pass pass = PassIds.TWO_STEP.equals(passId) ? twoStep : oneStep;

        String courseKey = chooseCourseKey(pass, scores, primary);
        String reason = buildReason(pass, courseKey, primary, adventure, relaxFood);

        return new Recommendation(passId, courseKey, primary, reason);
    }

    private String choosePass(TravelType primary, int adventure, int relaxFood, Pass twoStep) {
        Set<String> twoStepCourses = courseKeys(twoStep);

        if (primary == TravelType.EXPERIENCE || primary == TravelType.NIGHT) {
            return PassIds.TWO_STEP;
        }
        if (adventure >= relaxFood + 2) {
            return PassIds.TWO_STEP;
        }
        if (primary == TravelType.FOOD && adventure >= relaxFood) {
            return PassIds.TWO_STEP;
        }
        if (!twoStepCourses.contains(primary.courseKey()) && adventure > 0) {
            return PassIds.TWO_STEP;
        }
        return PassIds.ONE_STEP;
    }

    private String chooseCourseKey(Pass pass, EnumMap<TravelType, Integer> scores, TravelType primary) {
        Set<String> available = courseKeys(pass);

        if (available.contains(primary.courseKey())) {
            return primary.courseKey();
        }

        return scores.entrySet().stream()
                .sorted(Map.Entry.<TravelType, Integer>comparingByValue().reversed())
                .map(e -> e.getKey().courseKey())
                .filter(available::contains)
                .findFirst()
                .orElse(available.iterator().next());
    }

    private Set<String> courseKeys(Pass pass) {
        return pass.getCourses().stream()
                .map(Course::getCourseKey)
                .collect(Collectors.toSet());
    }

    private String buildReason(
            Pass pass,
            String courseKey,
            TravelType primary,
            int adventure,
            int relaxFood
    ) {
        String courseName = pass.getCourses().stream()
                .filter(c -> c.getCourseKey().equals(courseKey))
                .map(Course::getName)
                .findFirst()
                .orElse(Messages.DEFAULT_COURSE_NAME);

        if (adventure > relaxFood) {
            return String.format(
                    Messages.RECOMMENDATION_REASON_ADVENTURE,
                    primary.label(),
                    pass.getName(),
                    courseName
            );
        }
        return String.format(
                Messages.RECOMMENDATION_REASON,
                primary.label(),
                pass.getName(),
                courseName
        );
    }
}
