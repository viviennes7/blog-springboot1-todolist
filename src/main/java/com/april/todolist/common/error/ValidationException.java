package com.april.todolist.common.error;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
