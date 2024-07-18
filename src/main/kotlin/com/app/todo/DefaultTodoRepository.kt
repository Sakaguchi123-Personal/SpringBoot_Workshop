package com.app.todo

import org.springframework.stereotype.Repository

@Repository
class DefaultTodoRepository(val todoJpaRepository: TodoJpaRepository): TodoRepository {

    override fun findAll(): List<Todo> {
        todoJpaRepository.findAll()
        return listOf()
    }

    override fun save(todo: Todo) {
        val todoEntity = TodoEntity(todo.id,todo.name,todo.finished)
        todoJpaRepository.save(todoEntity)
    }
}