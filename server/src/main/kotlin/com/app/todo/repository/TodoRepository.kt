package com.app.todo.repository

import com.app.todo.model.Todo

interface TodoRepository {
    fun findAll(): List<Todo>
    fun save(todo: Todo)
    fun delete(id: Int)
    fun findById(id: Int): Todo?
}