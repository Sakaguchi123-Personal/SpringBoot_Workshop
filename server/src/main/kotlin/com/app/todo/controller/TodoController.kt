package com.app.todo.controller

import com.app.todo.model.Todo
import com.app.todo.service.TodoService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/todo")
class TodoController(val todoService: TodoService) {

    val log = LoggerFactory.getLogger(TodoController::class.java)

    @GetMapping
    fun getAllTodo(): List<Todo> {
//        log.trace("TRACE :Getting all Todos")
//        log.debug("DEBUG: Getting all Todos")
//        log.info("INFO: Getting all Todos")
//        log.warn("WARN: Getting all Todos")
//        log.error("ERROR: Getting all Todos")
        return todoService.getTodos()
    }

    // post mapping
    @PostMapping
    // return 201 Created
    @ResponseStatus(HttpStatus.CREATED)
    // receives request body of title
    fun postTodo(@RequestBody todo: Todo) {
        return todoService.postTodos(todo)
    }

    // overview: putmapping or patch mapping -> service -> repository -> jpaRepository.save
    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Int, @RequestBody @Validated updateRequest: TodoUpdateRequest): Todo {
        return todoService.updateTodo(id, Todo(null, updateRequest.name, updateRequest.finished))
    }


    // overview: deletemapping -> service -> repository -> jpaRepository.delete()
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTodo(@PathVariable id: Int) {
        todoService.deleteTodo(id)
    }
}