package com.smarttest.quizservice.dto.result.response;

import com.smarttest.quizservice.dto.quiz.response.QuizInfoResponse;
import com.smarttest.quizservice.dto.user.response.GeneralUserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private UUID id;

    private LocalDateTime createDate;

    private GeneralUserInfo user;

    private QuizInfoResponse quiz;

    private Integer rightAnswersNumber;
}
