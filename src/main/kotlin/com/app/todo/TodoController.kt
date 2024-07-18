package com.app.todo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoService: TodoService) {

    @GetMapping("/api/v1/todo")
    fun getAllTodo(): List<Todo> {
        return todoService.getTodos()
    }



}