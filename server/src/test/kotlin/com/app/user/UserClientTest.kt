package com.app.user

import com.app.user.client.UserClient
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.*
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient

class UserClientTest {

    @Test
    fun `users should call json place holder public API`() {
        val endpoint = "https://example.com"
        val restClientBuilder = RestClient.builder()
        val mockServer :MockRestServiceServer = MockRestServiceServer.bindTo(restClientBuilder).build()

        val responseBody = """
            [
                {
                    "id": 1,
                    "name": "Leanne Graham",
                    "username": "Bret"
                }
            ]
        """.trimIndent()

        mockServer.expect(requestTo("$endpoint/users"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess().body(responseBody).contentType(APPLICATION_JSON))

        val userClient = UserClient(restClientBuilder.build(), endpoint)
        userClient.users()

        mockServer.verify()
    }
}