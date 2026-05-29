package com.mungdori.localpath.application.survey.required;

import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.survey.UserSurvey;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserSurveyRepository extends Repository<UserSurvey, Long> {

    UserSurvey save(UserSurvey survey);

    Optional<UserSurvey> findByMember(Member member);
}
