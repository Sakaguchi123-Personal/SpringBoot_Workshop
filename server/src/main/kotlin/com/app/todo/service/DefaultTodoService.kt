package com.app.todo.service

import com.app.todo.model.Todo
import com.app.todo.model.exception.TodoNotFoundException
import com.app.todo.repository.TodoRepository
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

    override fun deleteTodo(id: Int) {
        todoRepository.delete(id)
    }

    override fun updateTodo(id: Int, updateTodo: Todo): Todo {
        val fetchedTodo = todoRepository.findById(id)
            ?: throw TodoNotFoundException("no todo found with id $id")

        val updatedTodo = Todo(fetchedTodo.id, updateTodo.name, updateTodo.finished)
        todoRepository.save(updatedTodo)

        return updatedTodo
    }
}