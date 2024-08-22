package com.clasp.taskidoo.controller;

import com.clasp.taskidoo.model.Task;
import com.clasp.taskidoo.model.TaskDto;
import com.clasp.taskidoo.model.TaskEditCommand;
import com.clasp.taskidoo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskService taskService;

    @Test
    void shouldGetTasks() throws Exception {
        // given
        List<Task> tasks = List.of(
                new Task(1L, "a", LocalDateTime.of(2020, 2, 3, 4, 0)),
                new Task(2L, "b", LocalDateTime.of(2020, 2, 3, 5, 0)),
                new Task(3L, "c", LocalDateTime.of(2020, 2, 3, 6, 0))
        );
        when(taskService.getTasks(any(Pageable.class))).thenReturn(tasks);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[2].id").value(3L));
    }

    @Test
    void shouldGetTask() throws Exception {
        // given
        Task task = new Task(1L, "a", LocalDateTime.of(2020, 2, 3, 4, 0));

        when(taskService.getTask(1L)).thenReturn(task);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dueTime").value("2020-02-03T04:00:00"))
                .andExpect(jsonPath("$.description").value("a"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // given
        TaskDto taskDto = new TaskDto(null, "a", LocalDateTime.of(2020, 2, 3, 4, 0));
        Task task = new Task(1L, "a", LocalDateTime.of(2020, 2, 3, 4, 0));
        when(taskService.createTask(any(TaskDto.class))).thenReturn(task);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(taskDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("a"))
                .andExpect(jsonPath("$.dueTime").value("2020-02-03T04:00:00"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // given
        TaskEditCommand taskEditCommand = new TaskEditCommand("updated description", LocalDateTime.of(2020, 2, 4, 10, 0));
        Task updatedTask = new Task(1L, "updated description", LocalDateTime.of(2020, 2, 4, 10, 0));
        when(taskService.updateTask(1L, taskEditCommand)).thenReturn(updatedTask);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskEditCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("updated description"))
                .andExpect(jsonPath("$.dueTime").value("2020-02-04T10:00:00"));
    }

}
