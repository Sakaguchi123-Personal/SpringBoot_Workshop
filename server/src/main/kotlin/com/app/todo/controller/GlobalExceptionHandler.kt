package com.app.todo.controller

import com.app.todo.model.exception.TodoNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(TodoNotFoundException::class)
    fun handleTodoNotFoundException(ex: TodoNotFoundException): ResponseEntity<String> {
        log.error("Todo not found: {}", ex.message)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.message)
    }
}