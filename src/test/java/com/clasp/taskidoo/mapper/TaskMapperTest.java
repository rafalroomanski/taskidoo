package com.clasp.taskidoo.mapper;

import com.clasp.taskidoo.model.Task;
import com.clasp.taskidoo.model.TaskDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void shouldMapTaskToTaskDto() {
        // given
        Task task = new Task(1L, "Test description", LocalDateTime.of(2020, 2, 3, 4, 0));

        // when
        TaskDto taskDto = taskMapper.toDto(task);

        // then
        assertAll(
                () -> assertEquals(task.getId(), taskDto.id()),
                () -> assertEquals(task.getDescription(), taskDto.description()),
                () -> assertEquals(task.getDueTime(), taskDto.dueTime())
        );
    }

    @Test
    void shouldMapTaskDtoToTask() {
        // given
        TaskDto taskDto = new TaskDto(1L, "Test description", LocalDateTime.of(2020, 2, 3, 4, 0));

        // when
        Task task = taskMapper.toEntity(taskDto);

        // then
        assertAll(
                () -> assertEquals(taskDto.id(), task.getId()),
                () -> assertEquals(taskDto.description(), task.getDescription()),
                () -> assertEquals(taskDto.dueTime(), task.getDueTime())
        );
    }

    @Test
    void shouldMapTaskListToTaskDtoList() {
        // given
        List<Task> tasks = List.of(
                new Task(1L, "Task 1", LocalDateTime.of(2020, 2, 3, 4, 0)),
                new Task(2L, "Task 2", LocalDateTime.of(2020, 2, 3, 5, 0)),
                new Task(3L, "Task 3", LocalDateTime.of(2020, 2, 3, 6, 0))
        );

        // when
        List<TaskDto> taskDtos = taskMapper.toDtos(tasks);

        // then
        assertAll(
                () -> assertEquals(3, taskDtos.size()),
                () -> assertEquals(tasks.get(0).getId(), taskDtos.get(0).id()),
                () -> assertEquals(tasks.get(0).getDescription(), taskDtos.get(0).description()),
                () -> assertEquals(tasks.get(1).getId(), taskDtos.get(1).id()),
                () -> assertEquals(tasks.get(1).getDescription(), taskDtos.get(1).description()),
                () -> assertEquals(tasks.get(2).getId(), taskDtos.get(2).id()),
                () -> assertEquals(tasks.get(2).getDescription(), taskDtos.get(2).description())
        );
    }

}
