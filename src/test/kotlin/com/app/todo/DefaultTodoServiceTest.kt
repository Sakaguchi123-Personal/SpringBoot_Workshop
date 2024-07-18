package com.app.todo

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultTodoServiceTest {

    lateinit var spyStubTodoRepository: SpyStubTodoRepository
    lateinit var todoService: TodoService


    @BeforeEach
    fun setUp() {
        spyStubTodoRepository = SpyStubTodoRepository()
        todoService = DefaultTodoService(spyStubTodoRepository)
    }

    @Test
    fun `getTodos calls repository`() {
        // action: call service
        todoService.getTodos()


        // assert: verify repository called
        assertThat(spyStubTodoRepository.called, equalTo(true))

    }

    @Test
    fun `getTodos return todos`() {
        // set up data here
        val testTodo = Todo(2, "Learn spring", false)
        spyStubTodoRepository.setTodos(listOf(testTodo))

        // action: call service
        val actual = todoService.getTodos()


        // assert
        assertThat(actual[0].id, equalTo(testTodo.id))
        assertThat(actual[0].name, equalTo(testTodo.name))
        assertThat(actual[0].finished, equalTo(testTodo.finished))

    }

    @Test
    fun `postTodos calls repository`() {
        todoService.postTodos(Todo(1,"ringo",false))

        assertThat(spyStubTodoRepository.called, equalTo(true))
    }
}
