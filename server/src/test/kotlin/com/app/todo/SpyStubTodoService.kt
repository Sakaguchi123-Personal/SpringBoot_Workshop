package com.app

import com.app.todo.model.Todo
import com.app.todo.service.TodoService


class SpyStubTodoService: TodoService {

    var getTodosCalled = false
    private var todos: List<Todo> = listOf()

    var postTodosCalled = false

    var deleteTodoCalled = false
    var deleteTodoId: Int? = null

    var updateTodoCalled = false
    var updateTodoId: Int? = null
    var updateTodoBody: Todo? = null
    private var updateTodoReturnValue: Todo = Todo(null, "", false)


    override fun getTodos(): List<Todo> {
        getTodosCalled = true
        return todos
    }

    override fun postTodos(todo: Todo) {
        postTodosCalled = true
    }

    override fun deleteTodo(id: Int) {
        deleteTodoCalled = true
        deleteTodoId = id
    }

    override fun updateTodo(id: Int, updateTodo: Todo): Todo {
        updateTodoCalled = true
        updateTodoId = id
        updateTodoBody = updateTodo
        return updateTodoReturnValue
    }


    fun setGetTodosReturnValue(todos: List<Todo>) {
        this.todos = todos
    }

    fun setUpdateTodoReturnValue(todo: Todo) {
        this.updateTodoReturnValue = todo
    }

}