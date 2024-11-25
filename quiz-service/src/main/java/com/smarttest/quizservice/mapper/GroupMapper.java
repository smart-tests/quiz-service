package com.smarttest.quizservice.mapper;

import com.smarttest.quizservice.dao.entities.Group;
import com.smarttest.quizservice.dto.group.request.CreateGroupRequest;
import com.smarttest.quizservice.dto.group.response.CreateGroupResponse;
import com.smarttest.quizservice.dto.group.response.GroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {
        UserMapper.class
})
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "members", ignore = true)
    Group toGroupEntity(CreateGroupRequest request);

    @Mapping(target = "ownerId", source = "entity.owner.id")
    CreateGroupResponse toCreateGroupResponse(Group entity);

    @Mapping(target = "quizzesCount", expression = "java(entity.getGroupQuizzes().size())")
    GroupResponse toGroupResponse(Group entity);

    List<GroupResponse> toGroupResponseList(List<Group> entities);
}
