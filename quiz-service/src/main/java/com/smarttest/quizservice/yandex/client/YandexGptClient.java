package com.smarttest.quizservice.yandex.client;

import com.smarttest.quizservice.yandex.dto.YandexGptMessageDto;
import com.smarttest.quizservice.yandex.dto.request.YandexGptCompletionOptionsDto;
import com.smarttest.quizservice.yandex.dto.request.YandexGptRequest;
import com.smarttest.quizservice.yandex.dto.response.YandexGptResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class YandexGptClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${yandex-gpt.url}")
    private String url;

    @Value("${yandex-gpt.model-uri}")
    private String modelUri;

    @Value("${yandex-gpt.key}")
    private String key;

    public YandexGptResponse completion(List<YandexGptMessageDto> messages) {
        log.info("Запрос к yandex-gpt");

        YandexGptRequest body = YandexGptRequest.builder()
                .modelUri(modelUri)
                .completionOptions(YandexGptCompletionOptionsDto.builder()
                        .stream(false)
                        .temperature(0.6f)
                        .maxTokens("2000")
                        .build())
                .messages(messages)
                .build();

        HttpEntity<YandexGptRequest> request = new HttpEntity<>(body, getHeaders());

        ResponseEntity<YandexGptResponse> response = restTemplate.postForEntity(
                url,
                request,
                YandexGptResponse.class);

        log.debug("Ответ от yandex-gpt: {}", response);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException(String.format(
                    "Ошибка yandex-gpt: %s\n%s", response.getStatusCode(), response.getBody()
            ));
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Api-Key " + key);
        return headers;
    }
}
