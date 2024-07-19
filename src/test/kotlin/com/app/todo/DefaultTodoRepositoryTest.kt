package com.app.todo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class DefaultTodoRepositoryTest {


    @Test
    fun `findAll should call jpaRepository findAll`() {
        val mockTodoJpaRepository : TodoJpaRepository = mockk()
        val todoRepository = DefaultTodoRepository(mockTodoJpaRepository)

        every { mockTodoJpaRepository.findAll() } returns emptyList()


        todoRepository.findAll()


        verify { mockTodoJpaRepository.findAll() }
    }

    @Test
    fun `findAll should return todos`() {
        val mockTodoJpaRepository : TodoJpaRepository = mockk()
        val todoRepository = DefaultTodoRepository(mockTodoJpaRepository)

        every { mockTodoJpaRepository.findAll() } returns listOf(TodoEntity(1, "Learn Kotlin", false))


        val actual = todoRepository.findAll()


        assertThat(actual.size, equalTo(1))
        assertThat(actual[0].id, equalTo(1))
        assertThat(actual[0].name, equalTo("Learn Kotlin"))
        assertThat(actual[0].finished, equalTo(false))
    }

    /* this test has 2 assertions and might be difficult to maintain in the future */
    /*@Test
    fun `findAll should call jpaRepository and return todos`() {
        val mockTodoJpaRepository : TodoJpaRepository = mockk()
        val todoRepository = DefaultTodoRepository(mockTodoJpaRepository)

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
        val mockTodoJpaRepository : TodoJpaRepository = mockk()
        val todoRepository = DefaultTodoRepository(mockTodoJpaRepository)

        val todo = Todo(1, "Learn", false)

        every {mockTodoJpaRepository.save(TodoEntity(1,"Learn", false)) } returns TodoEntity(1, "Learn", false)


        todoRepository.save(todo)


        verify{ mockTodoJpaRepository.save(TodoEntity(1,"Learn", false))}
    }
}