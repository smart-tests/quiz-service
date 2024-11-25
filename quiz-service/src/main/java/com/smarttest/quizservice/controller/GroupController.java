package com.smarttest.quizservice.controller;

import com.smarttest.quizservice.dto.group.request.AddGroupMemberRequest;
import com.smarttest.quizservice.dto.group.request.AddGroupQuizRequest;
import com.smarttest.quizservice.dto.group.request.CreateGroupRequest;
import com.smarttest.quizservice.dto.group.response.CreateGroupResponse;
import com.smarttest.quizservice.dto.group.response.GroupQuizResponse;
import com.smarttest.quizservice.dto.group.response.GroupResponse;
import com.smarttest.quizservice.service.GroupQuizService;
import com.smarttest.quizservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;
    private final GroupQuizService groupQuizService;

    @PostMapping
    public ResponseEntity<CreateGroupResponse> createGroup(@RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupInfo(@PathVariable(name = "groupId") UUID groupId) {
        return ResponseEntity.ok(groupService.getGroup(groupId));
    }

    @PostMapping("/add-member")
    public ResponseEntity<GroupResponse> addGroupMember(@RequestBody AddGroupMemberRequest request) {
        return ResponseEntity.ok(groupService.addMember(request));
    }

    @PostMapping("/{groupId}/add-quiz")
    public ResponseEntity<GroupQuizResponse> addQuizToGroup(@PathVariable(name = "groupId") UUID groupId,
                                                            @RequestBody AddGroupQuizRequest request) {

        return ResponseEntity.ok(groupQuizService.addQuizForGroup(groupId, request));
    }

    @GetMapping("/{groupId}/quizzes")
    public ResponseEntity<List<GroupQuizResponse>> getGroupQuizzes(@PathVariable(name = "groupId") UUID groupId) {

        return ResponseEntity.ok(groupQuizService.getGroupQuizzes(groupId));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getCurrentUserGroups() {
        return ResponseEntity.ok(groupService.getCurrentUserGroups());
    }
}