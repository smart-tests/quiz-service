package com.smarttest.quizservice.controller;

import com.smarttest.quizservice.dto.quiz.GeneratedQuizResponse;
import com.smarttest.quizservice.dto.quiz.request.PassingQuizRequest;
import com.smarttest.quizservice.dto.quiz.request.QuizRequest;
import com.smarttest.quizservice.dto.quiz.response.PassingQuizResponse;
import com.smarttest.quizservice.dto.quiz.response.QuizForPassingInfo;
import com.smarttest.quizservice.dto.quiz.response.QuizInfoResponse;
import com.smarttest.quizservice.dto.result.response.ResultResponse;
import com.smarttest.quizservice.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public String hello() {
        return "hello world";
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneratedQuizResponse> generateQuiz(@RequestPart MultipartFile document,
                                                                       @RequestPart String questionsNumber) {

        return ResponseEntity.ok(quizService.generate(document, 5));
    }

    @PostMapping("/create")
    public ResponseEntity<QuizInfoResponse> createQuiz(@RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.saveQuiz(request));
    }

    @GetMapping("/all-created")
    public ResponseEntity<List<QuizInfoResponse>> getCurrentUserCreatedQuizzes() {
        return ResponseEntity.ok(quizService.getCurrentUserCreatedQuizzes());
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizInfoResponse> getQuizInfo(@PathVariable(name = "quizId") UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizInfo(quizId));
    }

    @GetMapping("/passing/{quizId}")
    public ResponseEntity<PassingQuizResponse> getQuizForPassing(@PathVariable(name = "quizId") UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizForPassing(quizId));
    }

    @PostMapping("/passing/{quizId}")
    public ResponseEntity<ResultResponse> checkPassedQuiz(@PathVariable(name = "quizId") UUID quizId,
                                                          @RequestBody PassingQuizRequest request) {

        return ResponseEntity.ok(quizService.checkPassedQuiz(quizId, request));
    }

    @GetMapping("/education")
    public ResponseEntity<List<QuizForPassingInfo>> getAvailableQuizzes() {
        return ResponseEntity.ok(quizService.getAvailableQuizzesForCurrentUser());
    }

    @GetMapping("/education/results")
    public ResponseEntity<List<ResultResponse>> getPassedQuizzes() {
        return ResponseEntity.ok(quizService.getCurrentUserResults());
    }

    @GetMapping("/teaching/results/{quizId}")
    public ResponseEntity<List<ResultResponse>> getQuizResults(@PathVariable(name = "quizId") UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizResults(quizId));
    }

    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> downloadQuiz() {
        byte[] bytes = quizService.exportQuizAsWordFile();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Test.docx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }
}
