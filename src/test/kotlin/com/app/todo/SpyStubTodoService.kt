package com.app.todo

class SpyStubTodoService: TodoService {

    var called = false
    private var todos: List<Todo> = listOf()

    override fun getTodos(): List<Todo> {
        called = true
        return todos
    }

    fun setTodos(todos: List<Todo>) {
        this.todos = todos
    }
}