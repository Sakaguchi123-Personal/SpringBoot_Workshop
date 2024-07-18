package com.app.todo

class SpyStubTodoRepository: TodoRepository {
    var called = false
    private var todos: List<Todo> = listOf()

    override fun findAll(): List<Todo> {
        called = true
        return todos
    }

    override fun save(todo: Todo) {
        called = true
    }

    fun setTodos(todo: List<Todo>) {
        this.todos = todo
    }
}