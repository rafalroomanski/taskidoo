package com.clasp.taskidoo.model;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class TaskServiceException extends ResponseStatusException {

    public TaskServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
