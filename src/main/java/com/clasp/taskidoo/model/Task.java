package com.clasp.taskidoo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime dueTime;

    public void update(TaskEditCommand taskEditCommand) {
        if (nonNull(taskEditCommand.dueTime())) {
            this.dueTime = taskEditCommand.dueTime();
        }
        if (nonNull(taskEditCommand.description())) {
            this.description = taskEditCommand.description();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
