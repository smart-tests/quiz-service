package com.smarttest.quizservice.yandex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YandexGptMessageDto {

    @JsonProperty("role")
    private String role;

    @JsonProperty("text")
    private String text;

    @Getter
    @RequiredArgsConstructor
    public enum Role {

        SYSTEM("system"),

        USER("user");

        private final String value;
    }
}
