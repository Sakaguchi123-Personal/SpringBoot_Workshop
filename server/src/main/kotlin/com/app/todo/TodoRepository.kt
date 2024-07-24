package com.app.todo

interface TodoRepository {
    fun findAll(): List<Todo>
    fun save(todo: Todo)
    fun delete(id: Int)
    fun findById(id: Int): Todo?
}