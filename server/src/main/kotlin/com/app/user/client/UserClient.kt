package com.app.user.client

import com.app.user.model.User
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class UserClient(val restClient : RestClient) {

    fun users(): List<User> {
        val users = restClient.get()
            .uri("https://jsonplaceholder.typicode.com/users")
            .retrieve()
            .body<List<User>>()

        return users!!
    }
}