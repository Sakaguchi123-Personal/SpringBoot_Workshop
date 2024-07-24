package com.app.todo

interface TodoService {
    fun getTodos() : List<Todo>
    fun postTodos(todo: Todo)
    fun deleteTodo(id: Int)
    fun updateTodo(id: Int, updateTodo: Todo): Todo
}