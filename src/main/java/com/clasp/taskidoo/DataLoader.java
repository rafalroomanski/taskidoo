package com.clasp.taskidoo;

import com.clasp.taskidoo.model.Task;
import com.clasp.taskidoo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private TaskRepository repository;

    @Override
    public void run(ApplicationArguments args) {
        repository.save(new Task(null, "task1", LocalDateTime.now()));
        repository.save(new Task(null, "task2", LocalDateTime.now()));

    }
}