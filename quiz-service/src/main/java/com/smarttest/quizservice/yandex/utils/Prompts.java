package com.smarttest.quizservice.yandex.utils;

public class Prompts {

    public static final String GENERATE_QUIZ_PROMPT = """
            Ты — составитель тестов. На основе переданного пользователем текста составь 5 вопросов 
            с 4 вариантами ответа, из которых только 1 правильный ответ. Представь результат в форме объекта JSON, 
            в котором каждый из 5 question, находящихся в result, содержит по 4 answer, каждый из которых содержит
            поля text(string) и isRight(boolean)
            """;
}
