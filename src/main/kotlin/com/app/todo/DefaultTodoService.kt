package com.app.todo

import org.springframework.stereotype.Service

@Service
class DefaultTodoService(val todoRepository: TodoRepository) : TodoService {

    override fun getTodos() : List<Todo> {
        return todoRepository.findAll()
    }

}