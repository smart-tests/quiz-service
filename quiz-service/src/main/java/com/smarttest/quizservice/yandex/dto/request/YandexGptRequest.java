package com.smarttest.quizservice.yandex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smarttest.quizservice.yandex.dto.YandexGptMessageDto;
import com.smarttest.quizservice.yandex.dto.request.YandexGptCompletionOptionsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class YandexGptRequest {

    @JsonProperty("modelUri")
    private String modelUri;

    @JsonProperty("completionOptions")
    private YandexGptCompletionOptionsDto completionOptions;

    @JsonProperty("messages")
    private List<YandexGptMessageDto> messages;
}
