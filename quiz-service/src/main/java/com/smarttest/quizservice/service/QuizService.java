package com.smarttest.quizservice.service;

import com.smarttest.quizservice.dao.entities.GroupQuiz;
import com.smarttest.quizservice.dao.entities.Question;
import com.smarttest.quizservice.dao.entities.QuestionType;
import com.smarttest.quizservice.dao.entities.Quiz;
import com.smarttest.quizservice.dao.entities.Result;
import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dao.repository.GroupQuizRepository;
import com.smarttest.quizservice.dao.repository.QuizRepository;
import com.smarttest.quizservice.dao.repository.ResultRepository;
import com.smarttest.quizservice.dao.repository.UserRepository;
import com.smarttest.quizservice.dto.quiz.GeneratedQuizResponse;
import com.smarttest.quizservice.dto.quiz.request.PassingQuestionRequest;
import com.smarttest.quizservice.dto.quiz.request.PassingQuizRequest;
import com.smarttest.quizservice.dto.quiz.request.QuizRequest;
import com.smarttest.quizservice.dto.quiz.response.PassingQuizResponse;
import com.smarttest.quizservice.dto.quiz.response.QuizForPassingInfo;
import com.smarttest.quizservice.dto.quiz.response.QuizInfoResponse;
import com.smarttest.quizservice.dto.result.response.ResultResponse;
import com.smarttest.quizservice.dto.user.SecUser;
import com.smarttest.quizservice.mapper.GroupQuizMapper;
import com.smarttest.quizservice.mapper.QuizMapper;
import com.smarttest.quizservice.mapper.ResultMapper;
import com.smarttest.quizservice.service.checking.AnswerCheckStrategy;
import com.smarttest.quizservice.util.ServiceException;
import com.smarttest.quizservice.yandex.dto.GeneratedQuestion;
import com.smarttest.quizservice.yandex.service.YandexGptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {

    private final FileService fileService;
    private final UserService userService;
    private final YandexGptService yandexGptService;
    private final QuizMapper quizMapper;
    private final ResultMapper resultMapper;
    private final GroupQuizMapper groupQuizMapper;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;
    private final GroupQuizRepository groupQuizRepository;
    private Map<QuestionType, AnswerCheckStrategy> answerCheckMap;

    @Autowired
    public void setAnswerCheckMap(List<AnswerCheckStrategy> strategies) {
        answerCheckMap = strategies.stream()
                .collect(Collectors.toMap(AnswerCheckStrategy::getType, Function.identity()));
    }

    public QuizInfoResponse getQuizInfo(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ServiceException(
                        404,
                        "Quiz not found: quizId=" + quizId
                ));
        return quizMapper.toQuizInfoResponse(quiz);
    }

    public List<QuizInfoResponse> getCurrentUserCreatedQuizzes() {
        SecUser secUser = userService.getCurrentSecUser();

        List<Quiz> quizzes = quizRepository.findAllByCreatorId(secUser.getId());
        return quizMapper.toQuizInfoResponseList(quizzes);
    }

    public GeneratedQuizResponse generate(MultipartFile file, Integer questionNumber) {
        String fileContent = fileService.processWordFile(file);
        List<GeneratedQuestion> questions = yandexGptService.generateQuiz(fileContent);

        return GeneratedQuizResponse.builder()
                .questions(quizMapper.toQuestionResponseList(questions))
                .build();
    }

    public QuizInfoResponse saveQuiz(QuizRequest request) {
        SecUser secUser = userService.getCurrentSecUser();
        Optional<User> userOpt = userRepository.findById(secUser.getId());
        if (userOpt.isEmpty()) throw new ServiceException(404, "User not found: " + secUser.getId());

        Quiz entity = quizMapper.toQuizEntity(request, userOpt.get());
        Quiz savedQuiz = quizRepository.save(entity);

        return quizMapper.toQuizInfoResponse(savedQuiz);
    }

    public PassingQuizResponse getQuizForPassing(UUID quizId) {
        SecUser secUser = userService.getCurrentSecUser();
        GroupQuiz groupQuiz = groupQuizRepository
                .findByGroupMemberIdAndQuizId(secUser.getId(), quizId)
                .orElseThrow(() -> new ServiceException(404, "Quiz not found: " + quizId));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(groupQuiz.getStartDate()) || now.isAfter(groupQuiz.getEndDate())) {
            throw new ServiceException(400, "Quiz is currently unavailable");
        }

        // TODO: проверить, нет ли у пользователя сохраненных прохождений теста

        return quizMapper.toPassingQuizResponse(groupQuiz.getQuiz());
    }

    public List<QuizForPassingInfo> getAvailableQuizzesForCurrentUser() {
        SecUser secUser = userService.getCurrentSecUser();
        List<GroupQuiz> availableGroupQuizzes = groupQuizRepository
                .findAllByGroupMemberIdAndCurrentDate(secUser.getId(), LocalDateTime.now());

        return groupQuizMapper.toQuizForPassingInfoList(availableGroupQuizzes);
    }

    public List<ResultResponse> getCurrentUserResults() {
        SecUser secUser = userService.getCurrentSecUser();
        List<Result> results = resultRepository.findAllByUserId(secUser.getId());

        return resultMapper.toResultResponseList(results);
    }

    public List<ResultResponse> getQuizResults(UUID quizId) {
        List<Result> quizzes = resultRepository.findAllByQuizId(quizId);
        return resultMapper.toResultResponseList(quizzes);
    }

    public ResultResponse checkPassedQuiz(UUID quizId, PassingQuizRequest request) {
        SecUser secUser = userService.getCurrentSecUser();
        User user = userRepository.findById(secUser.getId())
                .orElseThrow(() -> new ServiceException(
                        404,
                        "User not found: userId=" + secUser.getId()
                ));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ServiceException(
                        404,
                        "Quiz not found: quizId=" + quizId
                ));
        log.info("Start checking of passed quiz: quizId={}, userId={}", quizId, user.getId());

        List<Question> incorrectAnswers = new LinkedList<>();
        Map<UUID, PassingQuestionRequest> selectedMap = request.getQuestions().stream()
                .collect(Collectors.toMap(PassingQuestionRequest::getId, Function.identity()));

        for (Question question : quiz.getQuestions()) {
            PassingQuestionRequest questionRequest;

            if ((questionRequest = selectedMap.get(question.getId())) == null
                    || !answerCheckMap.get(question.getType()).isCorrect(questionRequest, question)) {

                incorrectAnswers.add(question);
            }
        }
        log.info("End checking of passed quiz: number of incorrect answers={}", incorrectAnswers.size());

        Result result = resultMapper
                .toResultEntity(user, quiz, quiz.getQuestions().size() - incorrectAnswers.size());
        Result savedResult = resultRepository.save(result);

        return resultMapper.toResultResponse(savedResult);
    }

    public byte[] exportQuizAsWordFile() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(new String("Привет МИР!!!!"));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            document.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new ServiceException(
                    500,
                    "Error when export quiz: eMsg=" + e.getMessage()
            );
        }
    }
}
