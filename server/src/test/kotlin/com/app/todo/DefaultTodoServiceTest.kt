package com.app

import com.app.todo.SpyStubTodoRepository
import com.app.todo.service.DefaultTodoService
import com.app.todo.model.Todo
import com.app.todo.model.exception.TodoNotFoundException
import com.app.todo.service.TodoService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DefaultTodoServiceTest {

    lateinit var spyStubTodoRepository: SpyStubTodoRepository
    lateinit var todoService: TodoService


    @BeforeEach
    fun setUp() {
        spyStubTodoRepository = SpyStubTodoRepository()
        todoService = DefaultTodoService(spyStubTodoRepository)
    }

    @Nested
    inner class GetTodos {

        @Test
        fun `getTodos calls repository`() {
            // action: call service
            todoService.getTodos()


            // assert: verify repository called
            assertThat(spyStubTodoRepository.findAllCalled, equalTo(true))
        }

        @Test
        fun `getTodos return todos`() {
            // set up data here
            val testTodo = Todo(2, "Learn spring", false)
            spyStubTodoRepository.setFindAllReturnValue(listOf(testTodo))

            // action: call service
            val actual = todoService.getTodos()

            // assert
            assertThat(actual[0].id, equalTo(testTodo.id))
            assertThat(actual[0].name, equalTo(testTodo.name))
            assertThat(actual[0].finished, equalTo(testTodo.finished))
        }
    }

    @Nested
    inner class PostTodos {

        @Test
        fun `postTodos calls repository`() {
            todoService.postTodos(Todo(1, "Learn spring", false))

            assertThat(spyStubTodoRepository.saveCalled, equalTo(true))
        }
    }

    @Nested
    inner class DeleteTodo {

        @Test
        fun `deleteTodo calls repository with ID`() {
            todoService.deleteTodo(999)

            assertThat(spyStubTodoRepository.deleteCalled, equalTo(true))
            assertThat(spyStubTodoRepository.deleteTodoId, equalTo(999))
        }
    }

    @Nested
    inner class UpdateTodo {
        @Test
        fun `should call repository findById`() {
            spyStubTodoRepository.setFindByIdReturnValue(Todo(999, "", true))


            todoService.updateTodo(999, Todo(null, "Learn spring", false))


            assertThat(spyStubTodoRepository.findByIdCalled, equalTo(true))
            assertThat(spyStubTodoRepository.findByIdTodoId, equalTo(999))
        }

        @Test
        fun `should call repository save with updated values`() {
            spyStubTodoRepository.setFindByIdReturnValue(Todo(999, "", true))


            todoService.updateTodo(999, Todo(null, "Learn spring", false))


            assertThat(spyStubTodoRepository.saveCalled, equalTo(true))
            assertThat(spyStubTodoRepository.saveCalledTodo, equalTo(Todo(999, "Learn spring", false)))
        }

        @Test
        fun `should return updated todo`() {
            spyStubTodoRepository.setFindByIdReturnValue(Todo(999, "", true))


            val updateTodo = todoService.updateTodo(999, Todo(null, "Learn spring", false))


            assertThat(updateTodo, equalTo(Todo(999, "Learn spring", false)))
        }

        @Test
        fun `should return error when todo is not found with id`() {
            spyStubTodoRepository.setFindByIdReturnValue(null)


            val err = assertThrows<TodoNotFoundException> {
                todoService.updateTodo(999, Todo(null, "Learn spring", false))
            }
            assertThat(err.message, equalTo("no todo found with id 999"))
        }
    }
}
