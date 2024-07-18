package com.app.todo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoService: TodoService) {

    @GetMapping("/api/v1/todo")
    fun getAllTodo(): List<Todo> {
        return todoService.getTodos()
    }

    // post mapping
    @PostMapping("/api/v1/todo")
    // return 201 Created
    @ResponseStatus(HttpStatus.CREATED)
    // receives request body of title
    fun postTodo(@RequestBody todo: Todo) {
        return todoService.postTodos(todo)
    }

    // calls service to create
}