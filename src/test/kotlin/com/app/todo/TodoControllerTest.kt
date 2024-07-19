package com.app.todo

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
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
        spyStubTodoService.setGetTodosReturnValue(listOf(testTodo))


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
        assertThat(spyStubTodoService.getTodosCalled, equalTo(true))
    }

    //post Request test------------------------------------------

    @Test
    fun `post returns http status created`() {
        val testTodo = Todo(1, "Learn Kotlin", false)

        //json conversion
        val objectMapper = ObjectMapper()
        val testJson = objectMapper.writeValueAsString(testTodo)

        mockMvc.perform(post("/api/v1/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testJson)
        ).andExpect(status().isCreated)
    }

    // calls service to create
    @Test
    fun `post should call TodoService postTodos`() {
        val testTodo = Todo(1, "Learn Kotlin", false)

        //json conversion
        val objectMapper = ObjectMapper()
        val testJson = objectMapper.writeValueAsString(testTodo)

        mockMvc.perform(post("/api/v1/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testJson))

        assertThat(spyStubTodoService.postTodosCalled, equalTo(true))
    }

}