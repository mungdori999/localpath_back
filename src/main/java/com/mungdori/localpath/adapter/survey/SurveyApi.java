package com.mungdori.localpath.adapter.survey;

import com.mungdori.localpath.adapter.AuthorizationUtil;
import com.mungdori.localpath.adapter.survey.dto.HomeResponse;
import com.mungdori.localpath.adapter.survey.dto.SubmitSurveyRequest;
import com.mungdori.localpath.adapter.survey.dto.SurveyQuestionResponse;
import com.mungdori.localpath.adapter.survey.dto.SurveyStatusResponse;
import com.mungdori.localpath.application.survey.SurveyService;
import com.mungdori.localpath.common.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.SURVEY)
public class SurveyApi {

    private final SurveyService surveyService;

    @GetMapping("/questions")
    public List<SurveyQuestionResponse> getQuestions() {
        return surveyService.getQuestions();
    }

    @GetMapping("/home")
    public HomeResponse getHome() {
        return surveyService.getHome(AuthorizationUtil.requireEmail());
    }

    @GetMapping("/me")
    public SurveyStatusResponse getMySurvey() {
        return surveyService.getStatus(AuthorizationUtil.requireEmail());
    }

    @PostMapping("/me")
    public SurveyStatusResponse submit(@RequestBody SubmitSurveyRequest request) {
        return surveyService.submit(AuthorizationUtil.requireEmail(), request.answers());
    }
}
