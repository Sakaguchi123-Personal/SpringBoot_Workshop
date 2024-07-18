package com.app.todo

interface TodoService {
    fun getTodos() : List<Todo>
    fun postTodos(todo: Todo)
}