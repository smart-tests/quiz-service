package com.smarttest.quizservice.yandex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YandexGptResultDto {

    @JsonProperty("alternatives")
    private List<YandexGptAlternativeDto> alternatives;

    @JsonProperty("usage")
    private YandexGptUsageDto usage;

    @JsonProperty("modelVersion")
    private String modelVersion;
}
