package com.app.todo

interface TodoRepository {

    fun findAll(): List<Todo>
}