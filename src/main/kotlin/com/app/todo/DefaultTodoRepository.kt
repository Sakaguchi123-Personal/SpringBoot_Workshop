package com.app.todo

import org.springframework.stereotype.Repository

@Repository
class DefaultTodoRepository(val todoJpaRepository: TodoJpaRepository): TodoRepository {

    override fun findAll(): List<Todo> {
        val todoValue = todoJpaRepository.findAll()

        /* same as JavaScript Array map
        todoValue.map(it => {
            return Todo(it.id, it.name, it.finished)
        })*/

        return todoValue.map { Todo(it.id, it.name, it.finished )}

    }

    override fun save(todo: Todo) {
        val todoEntity = TodoEntity(todo.id,todo.name,todo.finished)
        todoJpaRepository.save(todoEntity)
    }
}