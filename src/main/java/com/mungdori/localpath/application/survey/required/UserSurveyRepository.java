package com.mungdori.localpath.application.survey.required;

import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.survey.UserSurveyEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserSurveyRepository extends Repository<UserSurveyEntity, Long> {

    UserSurveyEntity save(UserSurveyEntity survey);

    Optional<UserSurveyEntity> findByMember(Member member);
}
