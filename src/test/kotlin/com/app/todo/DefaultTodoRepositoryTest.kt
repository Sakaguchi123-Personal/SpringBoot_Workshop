package com.app.todo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class DefaultTodoRepositoryTest {


    @Test
    fun `findAll should call jpaRepository`() {
        val mockTodoJpaRepository : TodoJpaRepository = mockk()
        val todoRepository = DefaultTodoRepository(mockTodoJpaRepository)

        every { mockTodoJpaRepository.findAll() } returns emptyList()


        todoRepository.findAll()


        verify { mockTodoJpaRepository.findAll() }
    }

    // test 2:

}