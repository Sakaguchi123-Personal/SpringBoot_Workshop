package com.app.todo.service

import com.app.todo.model.Todo

interface TodoService {
    fun getTodos() : List<Todo>
    fun postTodos(todo: Todo)
    fun deleteTodo(id: Int)
    fun updateTodo(id: Int, updateTodo: Todo): Todo
}