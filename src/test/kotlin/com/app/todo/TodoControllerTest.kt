package com.app.todo

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*

class TodoControllerTest {
    lateinit var mockMvc : MockMvc
    lateinit var spyStubTodoService: SpyStubTodoService

    @BeforeEach
    fun setUp() {
        spyStubTodoService = SpyStubTodoService()
        mockMvc = standaloneSetup(TodoController(spyStubTodoService))
            .build()
    }

    @Test
    fun `get returns http status ok`() {
        mockMvc.perform(get("/api/v1/todo"))
            .andExpect(status().isOk)
    }

    @Test
    fun `get returns list of todo`() {
        // set the return value I want for this test (stub)
        val testTodo = Todo(1, "Learn Kotlin", false)
        spyStubTodoService.setTodos(listOf(testTodo))


        val responseJson = """
            [
                {
                    "id": ${testTodo.id},
                    "name": "${testTodo.name}",
                    "finished": ${testTodo.finished}
                }
            ]
        """.trimIndent()

        // action
        // assert
        mockMvc.perform(get("/api/v1/todo"))
            // option 1: use json matcher
            .andExpect(content().json(responseJson, true))

            // option 2: use jsonPath matcher
            .andExpect(jsonPath("$[0].id", equalTo(testTodo.id)))
            .andExpect(jsonPath("$[0].name", equalTo(testTodo.name)))
            .andExpect(jsonPath("$[0].finished", equalTo(testTodo.finished)))
    }

    @Test
    fun `get should call TodoService getTodos`() {
        mockMvc.perform(get("/api/v1/todo"))

        // 3. verify spy
        assertThat(spyStubTodoService.called, equalTo(true))
    }
}