package com.app.todo

import org.springframework.stereotype.Service

@Service
class DefaultTodoService(val todoRepository: TodoRepository) : TodoService {

    //get data from DB
    override fun getTodos() : List<Todo> {
        return todoRepository.findAll()
    }

    //post to DB
    override fun postTodos(todo: Todo) {
        todoRepository.save(todo)
    }
}