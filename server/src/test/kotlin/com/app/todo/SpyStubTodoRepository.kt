package com.app.todo

import com.app.todo.model.Todo
import com.app.todo.repository.TodoRepository

class SpyStubTodoRepository: TodoRepository {

    var findAllCalled = false
    var saveCalled = false
    var saveCalledTodo: Todo? = null


    var deleteCalled = false
    var deleteTodoId: Int? = null

    var findByIdCalled = false
    var findByIdTodoId: Int? = null
    private var findByIdReturnValue: Todo? = null


    private var todos: List<Todo> = listOf()

    override fun findAll(): List<Todo> {
        findAllCalled = true
        return todos
    }

    override fun save(todo: Todo) {
        saveCalled = true
        saveCalledTodo = todo
    }

    override fun delete(id: Int) {
        deleteCalled = true
        deleteTodoId = id
    }

    override fun findById(id: Int): Todo? {
        findByIdCalled = true
        findByIdTodoId = id
        return findByIdReturnValue
    }

    fun setFindAllReturnValue(todo: List<Todo>) {
        this.todos = todo
    }

    fun setFindByIdReturnValue(todo: Todo?) {
        this.findByIdReturnValue = todo
    }
}