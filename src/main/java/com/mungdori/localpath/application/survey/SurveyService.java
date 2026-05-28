package com.mungdori.localpath.application.survey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.localpath.adapter.survey.dto.*;
import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.application.passes.required.PassRepository;
import com.mungdori.localpath.application.survey.required.UserSurveyRepository;
import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.common.constants.PassIds;
import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.passes.CourseEntity;
import com.mungdori.localpath.domain.passes.PassEntity;
import com.mungdori.localpath.domain.survey.TravelType;
import com.mungdori.localpath.domain.survey.UserSurveyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final UserSurveyRepository userSurveyRepository;
    private final MemberRepository memberRepository;
    private final PassRepository passRepository;
    private final SurveyScoringService scoringService;
    private final PassRecommendationEngine recommendationEngine;
    private final ObjectMapper objectMapper;

    public List<SurveyQuestionResponse> getQuestions() {
        return SurveyQuestions.all();
    }

    @Transactional(readOnly = true)
    public SurveyStatusResponse getStatus(String memberEmail) {
        Member member = findMember(memberEmail);
        return userSurveyRepository.findByMember(member)
                .map(this::toStatus)
                .orElseGet(() -> new SurveyStatusResponse(false, List.of(), null));
    }

    @Transactional(readOnly = true)
    public HomeResponse getHome(String memberEmail) {
        Member member = findMember(memberEmail);

        return userSurveyRepository.findByMember(member)
                .map(survey -> new HomeResponse(
                        true,
                        true,
                        parseTypeScores(survey.getScoresJson()),
                        buildRecommendation(survey)
                ))
                .orElseGet(() -> new HomeResponse(true, false, List.of(), null));
    }

    @Transactional
    public SurveyStatusResponse submit(String memberEmail, Map<String, String> answers) {
        validateAnswers(answers);

        Member member = findMember(memberEmail);
        EnumMap<TravelType, Integer> scores = scoringService.score(answers);
        TravelType primary = scoringService.primaryType(scores);

        PassEntity oneStep = passRepository.findById(PassIds.ONE_STEP)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.PASS_DATA_NOT_FOUND));
        PassEntity twoStep = passRepository.findById(PassIds.TWO_STEP)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.PASS_DATA_NOT_FOUND));

        PassRecommendationEngine.Recommendation rec =
                recommendationEngine.recommend(oneStep, twoStep, scores);

        String answersJson = toJson(answers);
        String scoresJson = toJson(scoresToMap(scores));

        UserSurveyEntity survey = userSurveyRepository.findByMember(member)
                .orElse(null);

        if (survey == null) {
            survey = UserSurveyEntity.create(
                    member,
                    answersJson,
                    scoresJson,
                    primary,
                    rec.passId(),
                    rec.courseKey()
            );
        } else {
            survey.update(answersJson, scoresJson, primary, rec.passId(), rec.courseKey());
        }

        userSurveyRepository.save(survey);
        return toStatus(survey);
    }

    private void validateAnswers(Map<String, String> answers) {
        if (answers == null || answers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SURVEY_ANSWERS_EMPTY);
        }
        for (String questionId : SurveyQuestions.QUESTION_IDS) {
            String answer = answers.get(questionId);
            if (answer == null || answer.isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        Messages.SURVEY_ANSWER_REQUIRED_PREFIX + questionId
                );
            }
        }
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MEMBER_NOT_FOUND));
    }

    private SurveyStatusResponse toStatus(UserSurveyEntity survey) {
        return new SurveyStatusResponse(
                true,
                parseTypeScores(survey.getScoresJson()),
                buildRecommendation(survey)
        );
    }

    private PassRecommendationResponse buildRecommendation(UserSurveyEntity survey) {
        PassEntity pass = passRepository.findById(survey.getRecommendedPassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.RECOMMENDED_PASS_NOT_FOUND));

        CourseEntity course = pass.getCourses().stream()
                .filter(c -> c.getCourseKey().equals(survey.getRecommendedCourseKey()))
                .findFirst()
                .orElse(pass.getCourses().getFirst());

        TravelType primary = TravelType.valueOf(survey.getPrimaryType());

        return new PassRecommendationResponse(
                pass.getId(),
                pass.getName(),
                pass.getImage(),
                course.getCourseKey(),
                course.getName(),
                course.getEmoji(),
                primary.name(),
                primary.label(),
                recommendationReason(pass, course, primary)
        );
    }

    private String recommendationReason(PassEntity pass, CourseEntity course, TravelType primary) {
        return String.format(
                Messages.RECOMMENDATION_REASON,
                primary.label(),
                pass.getName(),
                course.getName()
        );
    }

    private List<TravelTypeScoreResponse> parseTypeScores(String scoresJson) {
        try {
            Map<String, Integer> raw = objectMapper.readValue(scoresJson, new TypeReference<>() {});
            return raw.entrySet().stream()
                    .map(e -> {
                        TravelType type = TravelType.valueOf(e.getKey());
                        return new TravelTypeScoreResponse(
                                type.name(),
                                type.courseKey(),
                                type.label(),
                                type.description(),
                                e.getValue()
                        );
                    })
                    .sorted((a, b) -> Integer.compare(b.score(), a.score()))
                    .toList();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.SCORE_PARSE_FAILED);
        }
    }

    private Map<String, Integer> scoresToMap(EnumMap<TravelType, Integer> scores) {
        Map<String, Integer> map = new java.util.LinkedHashMap<>();
        scores.forEach((type, value) -> map.put(type.name(), value));
        return map;
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.JSON_CONVERT_FAILED);
        }
    }
}
