package com.app.todo

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TodoJpaRepositoryTest {

    @Autowired
    lateinit var repository: TodoJpaRepository

    @Test
    fun `findAll() returns all todos`() {
        val first = TodoEntity(1, "Learn Kotlin", false)
        val second = TodoEntity(2, "Learn Java", false)
        repository.saveAll(listOf(
            first, second
        ))

        val actual = repository.findAll()

        assertThat(actual.size, equalTo(2))
        assertThat(actual[0], equalTo(first))
        assertThat(actual[1], equalTo(second))
    }

}