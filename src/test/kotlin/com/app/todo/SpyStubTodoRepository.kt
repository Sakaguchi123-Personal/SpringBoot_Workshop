package com.app.todo

class SpyStubTodoRepository: TodoRepository {

    var findAllCalled = false
    var saveCalled = false

    private var todos: List<Todo> = listOf()

    override fun findAll(): List<Todo> {
        findAllCalled = true
        return todos
    }

    override fun save(todo: Todo) {
        saveCalled = true
    }

    fun setFindAllReturnValue(todo: List<Todo>) {
        this.todos = todo
    }
}