package com.clasp.taskidoo.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskDto(Long id, @NotNull String description, @NotNull LocalDateTime dueTime) {

}
