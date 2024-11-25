package com.smarttest.quizservice.yandex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smarttest.quizservice.yandex.dto.YandexGptMessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YandexGptAlternativeDto {

    @JsonProperty("message")
    private YandexGptMessageDto message;

    @JsonProperty("status")
    private String status;
}
