package com.smarttest.quizservice.yandex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class YandexGptUsageDto {

    @JsonProperty("inputTextTokens")
    private String inputTextTokens;

    @JsonProperty("completionTokens")
    private String completionTokens;

    @JsonProperty("totalTokens")
    private String totalTokens;
}
