package com.app.todo

import com.app.todo.controller.TodoUpdateRequest
import com.app.todo.model.Todo
import com.app.todo.repository.jpa.TodoJpaRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.jpa.show-sql=true",
    ]
)
@ActiveProfiles("h2")
class MainApplicationTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var todoJpaRepository: TodoJpaRepository

    @LocalServerPort
    val port: Int? = null

    @BeforeEach
    fun setUp() {
        todoJpaRepository.deleteAll()
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun `post endpoint should create a new todo in database`() {
        val requestBody = Todo(null, "Learn AWS", false)


        restTemplate.postForEntity("http://localhost:$port/api/v1/todo", requestBody, Unit::class.java)


        val actual = todoJpaRepository.findAll()
        assertThat(actual.size, equalTo(1))
    }

    @Test
    fun `get endpoint should return list of todos in database`() {
        val postBody = Todo(null, "Eat apple", false)
        restTemplate.postForEntity("http://localhost:$port/api/v1/todo", postBody, Unit::class.java)
        val postedId = todoJpaRepository.findAll()[0].id!!


        val response = restTemplate.getForEntity("http://localhost:$port/api/v1/todo", Array<Todo>::class.java)


        val responseBody = response.body!!
        assertThat(responseBody.size, equalTo(1))
        assertThat(responseBody[0].id, equalTo(postedId))
        assertThat(responseBody[0].name, equalTo("Eat apple"))
        assertThat(responseBody[0].finished, equalTo(false))
    }

    @Test
    fun `put endpoint should update values in database`() {
        val postBody = Todo(null, "Eat apple", false)
        restTemplate.postForEntity("http://localhost:$port/api/v1/todo", postBody, Unit::class.java)
        val postedId = todoJpaRepository.findAll()[0].id!!


        val requestBody = TodoUpdateRequest("Eat bread", true)
        restTemplate.put("http://localhost:$port/api/v1/todo/$postedId", requestBody)


        val actual = todoJpaRepository.findById(postedId).get()
        assertThat(actual.id, equalTo(postedId))
        assertThat(actual.name, equalTo("Eat bread"))
        assertThat(actual.finished, equalTo(true))
    }

    @Test
    fun `delete endpoint should delete todo from database`() {
        val postBody = Todo(null, "Eat apple", false)
        restTemplate.postForEntity("http://localhost:$port/api/v1/todo", postBody, Unit::class.java)
        val postedId = todoJpaRepository.findAll()[0].id!!


        restTemplate.delete("http://localhost:$port/api/v1/todo/$postedId")


        val actual = todoJpaRepository.findById(postedId)
        assertThat(actual.isEmpty, equalTo(true))
    }
}
