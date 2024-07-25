package com.app.todo

import com.app.SpyStubTodoService
import com.app.todo.controller.TodoController
import com.app.todo.controller.TodoUpdateRequest
import com.app.todo.model.Todo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class TodoControllerTest {
    lateinit var mockMvc : MockMvc
    lateinit var spyStubTodoService: SpyStubTodoService
    val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        spyStubTodoService = SpyStubTodoService()
        mockMvc = standaloneSetup(TodoController(spyStubTodoService))
            .build()
    }

    @Nested
    inner class GetAllTodo {
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
    }



    @Nested
    inner class PostTodo {
        @Test
        fun `post returns http status created`() {
            val testTodo = Todo(1, "Learn Kotlin", false)

            //json conversion
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
            val testJson = objectMapper.writeValueAsString(testTodo)

            mockMvc.perform(post("/api/v1/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testJson))

            assertThat(spyStubTodoService.postTodosCalled, equalTo(true))
        }
    }

    @Nested
    inner class DeleteTodo {
        @Test
        fun `delete returns http status no content(204)`() {
            mockMvc.perform(delete("/api/v1/todo/1"))
                .andExpect(status().isNoContent)
        }

        @Test
        fun `delete should call TodoService deleteTodo`() {
            mockMvc.perform(delete("/api/v1/todo/1231233"))

            assertThat(spyStubTodoService.deleteTodoCalled, equalTo(true))
            assertThat(spyStubTodoService.deleteTodoId, equalTo(1231233))
        }
    }

    @Nested
    inner class UpdateTodo {
        @Test
        fun `update returns http status ok(200)`() {

            val putTodo = TodoUpdateRequest("Eat bread", false)
            val putJson = objectMapper.writeValueAsString(putTodo)

            mockMvc.perform(put("/api/v1/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(putJson))
                .andExpect(status().isOk)
        }

        @Test
        fun `update should call TodoService updateTodo`() {
            val putTodo = TodoUpdateRequest("Eat bread", false)
            val putJson = objectMapper.writeValueAsString(putTodo)

            mockMvc.perform(put("/api/v1/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(putJson))

            assertThat(spyStubTodoService.updateTodoCalled, equalTo(true))
            assertThat(spyStubTodoService.updateTodoId, equalTo(1))
            assertThat(spyStubTodoService.updateTodoBody, equalTo(Todo(null, "Eat bread", false)))
        }

        @Test
        fun `update should return updatedTodo`() {
            val putTodo = TodoUpdateRequest("Eat bread", false)
            val putJson = objectMapper.writeValueAsString(putTodo)

            spyStubTodoService.setUpdateTodoReturnValue(Todo(1, "Eat bread", false))


            val returnedJson = mockMvc.perform(put("/api/v1/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(putJson))
                .andReturn()
                .response
                .contentAsString // json string return


            // we can use object mapper to map json string to Kotlin class
            val returnedTodo = objectMapper.readValue<Todo>(returnedJson)
            assertThat(returnedTodo.id, equalTo(1))
            assertThat(returnedTodo.name, equalTo("Eat bread"))
            assertThat(returnedTodo.finished, equalTo(false))
        }
    }
}