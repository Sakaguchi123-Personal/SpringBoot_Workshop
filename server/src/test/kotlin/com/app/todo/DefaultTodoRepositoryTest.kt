package com.app.todo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class DefaultTodoRepositoryTest {
    lateinit var mockTodoJpaRepository: TodoJpaRepository
    lateinit var todoRepository: TodoRepository

    @BeforeEach
    fun setUp() {
        mockTodoJpaRepository = mockk()
        todoRepository = DefaultTodoRepository(mockTodoJpaRepository)
    }

    @Test
    fun `findAll should call jpaRepository findAll`() {

        every { mockTodoJpaRepository.findAll() } returns emptyList()


        todoRepository.findAll()


        verify { mockTodoJpaRepository.findAll() }
    }

    @Test
    fun `findAll should return todos`() {

        every { mockTodoJpaRepository.findAll() } returns listOf(TodoEntity(1, "Learn Kotlin", false))


        val actual = todoRepository.findAll()


        assertThat(actual.size, equalTo(1))
        assertThat(actual[0].id, equalTo(1))
        assertThat(actual[0].name, equalTo("Learn Kotlin"))
        assertThat(actual[0].finished, equalTo(false))
    }

    /* this test has 2 assertions and might be difficult to maintain in the future *//*@Test
    fun `findAll should call jpaRepository and return todos`() {

        every { mockTodoJpaRepository.findAll() } returns listOf(TodoEntity(1, "Learn Kotlin", false))


        val actual = todoRepository.findAll()


        assertThat(actual.size, equalTo(1))
        assertThat(actual[0].id, equalTo(1))
        assertThat(actual[0].name, equalTo("Learn Kotlin"))
        assertThat(actual[0].finished, equalTo(false))

        verify { mockTodoJpaRepository.findAll() }
    }*/

    @Test
    fun `save should call jpaRepository save`() {

        val todo = Todo(1, "Learn", false)
        val todoEntity = TodoEntity(1, "Learn", false)

        every { mockTodoJpaRepository.save(todoEntity) } returns TodoEntity(1, "Learn", false)


        todoRepository.save(todo)


        verify { mockTodoJpaRepository.save(todoEntity) }
    }

    @Test
    fun `delete should call jpaRepository deleteById`() {
        val id = 999
        every { mockTodoJpaRepository.deleteById(id) } returns Unit

        todoRepository.delete(id)

        verify { mockTodoJpaRepository.deleteById(id) }
    }

    @Test
    fun `findById should call jpaRepository findById`() {
        val id = 999

        every { mockTodoJpaRepository.findById(id)} returns Optional.of(TodoEntity(999, "", false))

        todoRepository.findById(id)

        verify { mockTodoJpaRepository.findById(id) }
    }

    @Test
    fun `findById should return todo`() {
        val id = 999
        val name = "Learn js"
        val finished = false

        every { mockTodoJpaRepository.findById(id)} returns Optional.of(TodoEntity(id, name, finished))

        val actual = todoRepository.findById(id)

        assertThat(actual!!.id, equalTo(id))
        assertThat(actual.name, equalTo(name))
        assertThat(actual.finished, equalTo(finished))
    }

    @Test
    fun `findById should return null when todo is not found`() {
        val id = 999

        every { mockTodoJpaRepository.findById(id)} returns Optional.empty()

        val actual = todoRepository.findById(id)

        assertThat(actual, equalTo(null))
    }
}