package com.clasp.taskidoo.service;

import com.clasp.taskidoo.mapper.TaskMapper;
import com.clasp.taskidoo.model.Task;
import com.clasp.taskidoo.model.TaskDto;
import com.clasp.taskidoo.model.TaskEditCommand;
import com.clasp.taskidoo.model.TaskServiceException;
import com.clasp.taskidoo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<Task> getTasks(Pageable pageable) {
        Pageable pageableParameters = enrichPageable(pageable);
        return taskRepository.findAll(pageableParameters).getContent();
    }

    public Task createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        return taskRepository.save(task);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskServiceException(HttpStatusCode.valueOf(404), "Task with id: '{}' does not exist."));
    }

    @Transactional
    public Task updateTask(Long id, TaskEditCommand taskEditCommand) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskServiceException(HttpStatusCode.valueOf(404), "Task with id: '{}' does not exist."));
        task.update(taskEditCommand);
        return task;
    }

    private Pageable enrichPageable(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            log.info("Sort parameter is empty. Default sorting by 'dueTime' will be used.");
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dueTime"));
        }
        return pageable;
    }

}
