package com.smarttest.quizservice.service;

import com.smarttest.quizservice.dao.entities.Group;
import com.smarttest.quizservice.dao.entities.GroupQuiz;
import com.smarttest.quizservice.dao.entities.Quiz;
import com.smarttest.quizservice.dao.repository.GroupQuizRepository;
import com.smarttest.quizservice.dao.repository.GroupRepository;
import com.smarttest.quizservice.dao.repository.QuizRepository;
import com.smarttest.quizservice.dto.group.request.AddGroupQuizRequest;
import com.smarttest.quizservice.dto.group.response.GroupQuizResponse;
import com.smarttest.quizservice.dto.user.SecUser;
import com.smarttest.quizservice.mapper.GroupQuizMapper;
import com.smarttest.quizservice.util.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupQuizService {

    private final GroupRepository groupRepository;
    private final QuizRepository quizRepository;
    private final GroupQuizRepository groupQuizRepository;
    private final UserService userService;
    private final GroupQuizMapper groupQuizMapper;

    public List<GroupQuizResponse> getGroupQuizzes(UUID groupId) {
        SecUser secUser = userService.getCurrentSecUser();
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new ServiceException(404, "Group not found: " + groupId);
        });

        if (!group.getOwner().getId().equals(secUser.getId())) {
            throw new ServiceException(403, "Current user is not group owner");
        }

        return groupQuizMapper.toGroupQuizResponseList(group.getGroupQuizzes());
    }

    public GroupQuizResponse addQuizForGroup(UUID groupId, AddGroupQuizRequest request) {
        SecUser secUser = userService.getCurrentSecUser();
        Optional<GroupQuiz> groupQuizOpt = groupQuizRepository.findGroupQuizByGroupIdAndQuizId(groupId, request.getQuizId());

        if (groupQuizOpt.isPresent()) {
            throw new ServiceException(400, "Quiz already added to group");
        }

        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) throw new ServiceException(404, "Group not found: " + groupId);
        if (!groupOpt.get().getOwner().getId().equals(secUser.getId())) {
            throw new ServiceException(403, "Current user is not group owner");
        }

        Optional<Quiz> quizOpt = quizRepository.findById(request.getQuizId());
        if (quizOpt.isEmpty()) throw new ServiceException(404, "Quiz not found: " + request.getQuizId());
        if (!quizOpt.get().getCreator().getId().equals(secUser.getId())) {
            throw new ServiceException(403, "Current user is not quiz creator");
        }

        GroupQuiz groupQuiz = groupQuizMapper.toGroupQuizEntity(request, groupOpt.get(), quizOpt.get());
        GroupQuiz savedGroupQuiz = groupQuizRepository.save(groupQuiz);

        return groupQuizMapper.toGroupQuizResponse(savedGroupQuiz);
    }
}
