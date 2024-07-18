package com.app.todo

import org.springframework.http.ResponseEntity

class SpyStubTodoService: TodoService {

    var called = false
    private var todos: List<Todo> = listOf()

    override fun getTodos(): List<Todo> {
        called = true
        return todos
    }

    override fun postTodos(todo: Todo) {
        called = true
    }

    fun setTodos(todos: List<Todo>) {
        this.todos = todos
    }

}