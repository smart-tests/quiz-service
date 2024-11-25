package com.smarttest.quizservice.yandex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttest.quizservice.dto.quiz.GeneratedQuizResponse;
import com.smarttest.quizservice.dto.quiz.GeneratedQuizQuestionDto;
import com.smarttest.quizservice.yandex.client.YandexGptClient;
import com.smarttest.quizservice.yandex.dto.GeneratedQuestion;
import com.smarttest.quizservice.yandex.dto.GeneratedResult;
import com.smarttest.quizservice.yandex.dto.YandexGptMessageDto;
import com.smarttest.quizservice.yandex.dto.response.YandexGptAlternativeDto;
import com.smarttest.quizservice.yandex.dto.response.YandexGptResponse;
import com.smarttest.quizservice.yandex.utils.Prompts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class YandexGptService {

    private final YandexGptClient yandexGptClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<GeneratedQuestion> generateQuiz(String text) {
        List<YandexGptMessageDto> messages = List.of(
                YandexGptMessageDto.builder()
                        .role(YandexGptMessageDto.Role.SYSTEM.getValue())
                        .text(Prompts.GENERATE_QUIZ_PROMPT)
                        .build(),
                YandexGptMessageDto.builder()
                        .role(YandexGptMessageDto.Role.USER.getValue())
                        .text(text)
                        .build());

        YandexGptResponse response = yandexGptClient.completion(messages);
        List<GeneratedQuestion> questions = new ArrayList<>();
        String preparedQuiz = null;

        try {
            for (YandexGptAlternativeDto alternative : response.getResult().getAlternatives()) {
                preparedQuiz = alternative.getMessage().getText().replace("`", "");
                questions.addAll(objectMapper.readValue(preparedQuiz, GeneratedResult.class).getResult());
            }
        } catch (JsonProcessingException e) {
            log.error("Ошибка при попытке маппинга ответа от yandex-gpt:\n{}", preparedQuiz);
            throw new RuntimeException(e);
        }

        return questions;
    }
}
