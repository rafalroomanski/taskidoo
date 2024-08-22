package com.clasp.taskidoo.model;

import java.time.LocalDateTime;

public record TaskEditCommand(String description, LocalDateTime dueTime) {
}
