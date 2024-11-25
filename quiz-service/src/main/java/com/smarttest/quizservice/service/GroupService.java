package com.smarttest.quizservice.service;

import com.smarttest.quizservice.dao.entities.Group;
import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dao.repository.GroupRepository;
import com.smarttest.quizservice.dao.repository.UserRepository;
import com.smarttest.quizservice.dto.group.request.AddGroupMemberRequest;
import com.smarttest.quizservice.dto.group.request.AddGroupQuizRequest;
import com.smarttest.quizservice.dto.group.request.CreateGroupRequest;
import com.smarttest.quizservice.dto.group.response.CreateGroupResponse;
import com.smarttest.quizservice.dto.group.response.GroupResponse;
import com.smarttest.quizservice.dto.user.SecUser;
import com.smarttest.quizservice.mapper.GroupMapper;
import com.smarttest.quizservice.util.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final GroupMapper groupMapper;

    public List<GroupResponse> getCurrentUserGroups() {
        SecUser secUser = userService.getCurrentSecUser();

        List<Group> groups = groupRepository.findAllByOwnerId(secUser.getId());
        return groupMapper.toGroupResponseList(groups);
    }

    public GroupResponse getGroup(UUID id) {
        Optional<Group> groupOpt = groupRepository.findById(id);
        if (groupOpt.isEmpty()) {
            throw new ServiceException(404, "Group not found: " + id);
        }

        return groupMapper.toGroupResponse(groupOpt.get());
    }

    public CreateGroupResponse createGroup(CreateGroupRequest request) {
        Group newGroup = groupMapper.toGroupEntity(request);
        SecUser secUser = userService.getCurrentSecUser();

        Optional<User> userOpt = userRepository.findById(secUser.getId());

        if (userOpt.isEmpty()) {
            throw new ServiceException(404 ,"User not found: " + secUser.getId());
        }

        newGroup.setOwner(userOpt.get());
        Group savedGroup = groupRepository.save(newGroup);

        return groupMapper.toCreateGroupResponse(savedGroup);
    }

    public GroupResponse addMember(AddGroupMemberRequest request) {
        SecUser secUser = userService.getCurrentSecUser();

        Optional<Group> groupOpt = groupRepository.findById(request.getGroupId());
        if (groupOpt.isEmpty()) throw new ServiceException(404, "Group not found " + request.getGroupId());

        if (!groupOpt.get().getOwner().getId().equals(secUser.getId())) {
            throw new ServiceException(403, "Current user is not group owner");
        }

        Optional<User> userOpt = userRepository.findByLogin(request.getMemberLogin());
        if (userOpt.isEmpty()) {
            throw new ServiceException(404, "User not found: userLogin=" + request.getMemberLogin());
        }

        if (groupOpt.get().getOwner().getId().equals(userOpt.get().getId())) {
            throw new ServiceException(400, "Cannot add group owner to group members");
        }

        groupOpt.get().getMembers().add(userOpt.get());
        Group savedGroup = groupRepository.save(groupOpt.get());

        return groupMapper.toGroupResponse(savedGroup);
    }
}
