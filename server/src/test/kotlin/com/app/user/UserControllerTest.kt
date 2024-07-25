package com.app.user

import com.app.user.client.UserClient
import com.app.user.controller.UserController
import com.app.user.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class UserControllerTest {

    @Test
    fun `getUsers should return 200 (OK)`() {
        val mockUserClient = mockk<UserClient>()
        every { mockUserClient.users() } returns emptyList()

        val mockMvc = standaloneSetup(UserController(mockUserClient))
            .build()


        mockMvc.perform(get("/api/v1/users"))
            .andExpect(status().isOk)
    }

    @Test
    fun `getUsers should call UserClient`() {
        val mockUserClient = mockk<UserClient>()
        every { mockUserClient.users() } returns emptyList()

        val mockMvc = standaloneSetup(UserController(mockUserClient))
            .build()


        mockMvc.perform(get("/api/v1/users"))


        verify { mockUserClient.users() }
    }

    @Test
    fun `getUsers should return users`() {
        val mockUserClient = mockk<UserClient>()

        every {mockUserClient.users()} returns listOf(User(1, "Leanne Graham", "Bret"))


        val mockMvc = standaloneSetup(UserController(mockUserClient))
            .build()

        val responseJson = """
            [
                {
                    "id": 1,
                    "name": "Leanne Graham",
                    "username": "Bret"
                }
            ]
        """.trimIndent()

        mockMvc.perform(get("/api/v1/users"))
            .andExpect(content().json(responseJson, true))

    }
}