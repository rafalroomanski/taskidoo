package com.clasp.taskidoo.mapper;

import com.clasp.taskidoo.model.Task;
import com.clasp.taskidoo.model.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto task);

    List<TaskDto> toDtos(List<Task> tasks);

}
