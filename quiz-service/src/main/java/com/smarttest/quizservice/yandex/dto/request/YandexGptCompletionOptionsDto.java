package com.smarttest.quizservice.yandex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class YandexGptCompletionOptionsDto {

    @JsonProperty("stream")
    private Boolean stream;

    @JsonProperty("temperature")
    private Float temperature;

    @JsonProperty("maxTokens")
    private String maxTokens;
}
