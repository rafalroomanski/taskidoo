package com.clasp.taskidoo.service;

import com.clasp.taskidoo.mapper.TaskMapper;
import com.clasp.taskidoo.model.Task;
import com.clasp.taskidoo.model.TaskDto;
import com.clasp.taskidoo.model.TaskEditCommand;
import com.clasp.taskidoo.model.TaskServiceException;
import com.clasp.taskidoo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    TaskService taskService;
    TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        this.taskRepository = mock(TaskRepository.class);
        this.taskService = new TaskService(taskRepository, Mappers.getMapper(TaskMapper.class));
    }

    @Test
    void getTasks_dataCorrect_tasksReturned() {
        // given
        Pageable pageable = PageRequest.of(1, 10);
        Page<Task> mockedTasks = new PageImpl<>(List.of(
                new Task(1L, "a", LocalDateTime.of(2020, 2, 3, 4, 0)),
                new Task(2L, "b", LocalDateTime.of(2020, 2, 3, 5, 0)),
                new Task(3L, "c", LocalDateTime.of(2020, 2, 3, 6, 0))));
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(mockedTasks);

        // when
        List<Task> tasks = taskService.getTasks(pageable);

        // then
        assertAll(
                () -> assertEquals(3, tasks.size()),
                () -> assertEquals("a", tasks.get(0).getDescription()),
                () -> assertEquals(2L, tasks.get(1).getId()),
                () -> assertEquals(LocalDateTime.of(2020, 2, 3, 5, 0), tasks.get(1).getDueTime()),
                () -> assertEquals("b", tasks.get(1).getDescription()),
                () -> assertEquals("c", tasks.get(2).getDescription())
        );
    }

    @Test
    void createTask_dataCorrect_taskCreated() {
        // given
        TaskDto taskDto = new TaskDto(null, "a", LocalDateTime.of(2020, 2, 3, 4, 0));
        Task mockedTask = new Task(1L, "a", LocalDateTime.of(2020, 2, 3, 4, 0));
        when(taskRepository.save(any())).thenReturn(mockedTask);

        // when
        Task task = taskService.createTask(taskDto);

        // then
        assertAll(
                () -> assertEquals("a", task.getDescription()),
                () -> assertEquals(1L, task.getId()),
                () -> assertEquals(LocalDateTime.of(2020, 2, 3, 4, 0), task.getDueTime()));
    }

    @Test
    void getTask_dataCorrect_taskReturned() {
        // given
        Task mockedTask = new Task(1L, "a", LocalDateTime.of(2020, 2, 3, 4, 0));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockedTask));

        // when
        Task task = taskService.getTask(1L);

        // then
        assertAll(
                () -> assertEquals("a", task.getDescription()),
                () -> assertEquals(1L, task.getId()),
                () -> assertEquals(LocalDateTime.of(2020, 2, 3, 4, 0), task.getDueTime()));
    }

    @Test
    void getTask_taskNotFound_exceptionThrown() {
        // given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        TaskServiceException taskServiceException = assertThrows(TaskServiceException.class, () -> taskService.getTask(1L));
        // then
        assertAll(
                () -> assertEquals(HttpStatusCode.valueOf(404), taskServiceException.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"Task with id: '{}' does not exist.\"", taskServiceException.getMessage()));
    }

    @Test
    void updateTask_dataCorrect_taskUpdated() {
        // given
        TaskEditCommand taskEditCommand = new TaskEditCommand("updated description", LocalDateTime.of(2020, 2, 4, 10, 0));
        Task existingTask = new Task(1L, "old description", LocalDateTime.of(2020, 2, 3, 4, 0));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any())).thenReturn(existingTask);

        // when
        Task updatedTask = taskService.updateTask(1L, taskEditCommand);

        // then
        assertAll(
                () -> assertEquals(1L, updatedTask.getId()),
                () -> assertEquals("updated description", updatedTask.getDescription()),
                () -> assertEquals(LocalDateTime.of(2020, 2, 4, 10, 0), updatedTask.getDueTime())
        );
    }

    @Test
    void updateTask_taskNotFound_exceptionThrown() {
        // given
        TaskEditCommand taskEditCommand = new TaskEditCommand("new description", LocalDateTime.of(2020, 2, 4, 10, 0));
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        TaskServiceException taskServiceException = assertThrows(TaskServiceException.class, () -> taskService.updateTask(1L, taskEditCommand));

        // then
        assertAll(
                () -> assertEquals(HttpStatusCode.valueOf(404), taskServiceException.getStatusCode()),
                () -> assertEquals("404 NOT_FOUND \"Task with id: '{}' does not exist.\"", taskServiceException.getMessage())
        );
    }

}
