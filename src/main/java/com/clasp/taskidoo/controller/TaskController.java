package com.clasp.taskidoo.controller;

import com.clasp.taskidoo.mapper.TaskMapper;
import com.clasp.taskidoo.model.TaskDto;
import com.clasp.taskidoo.model.TaskEditCommand;
import com.clasp.taskidoo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Operation(
            summary = "Get a paginated list of tasks",
            description = "Retrieve a list of tasks with pagination and sorting options. If no sort parameter is provided, tasks will be sorted by 'dueTime' by default."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tasks",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)))})
    @GetMapping
    public List<TaskDto> getTasks(Pageable pageable) {
        log.info("Received get tasks request with parameters: {}", pageable.toString());
        return taskMapper.toDtos(taskService.getTasks(pageable));
    }

    @Operation(
            summary = "Create a new task",
            description = "Create a new task by providing the necessary details. The task description and due time are required."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the task",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid task data", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto) {
        log.info("Received create task request with data: {}", taskDto.toString());
        return taskMapper.toDto(taskService.createTask(taskDto));
    }

    @Operation(
            summary = "Get task by ID",
            description = "Retrieve a specific task by its ID. If the task does not exist, a 404 error will be returned."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the task",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        log.info("Received get task by id '{}' request", id);
        return taskMapper.toDto(taskService.getTask(id));
    }

    @Operation(
            summary = "Update an existing task",
            description = "Update the details of an existing task by providing the new description or due time. The task ID must be valid."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the task",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
    })
    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskEditCommand taskEditCommand) {
        log.info("Received update task with id {} request with data: {}", id, taskEditCommand.toString());
        return taskMapper.toDto(taskService.updateTask(id, taskEditCommand));
    }

}
