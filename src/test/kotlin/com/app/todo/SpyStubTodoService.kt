package com.app.todo


class SpyStubTodoService: TodoService {

    var getTodosCalled = false
    var postTodosCalled = false
    private var todos: List<Todo> = listOf()

    override fun getTodos(): List<Todo> {
        getTodosCalled = true
        return todos
    }

    override fun postTodos(todo: Todo) {
        postTodosCalled = true
    }

    fun setGetTodosReturnValue(todos: List<Todo>) {
        this.todos = todos
    }

}