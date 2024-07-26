package com.app.user.client

import com.app.user.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class UserClient(val restClient : RestClient,
                 @Value("\${public-api.url}") val publicApiUrl: String) {


    fun users(): List<User> {

        println("public-api.url: $publicApiUrl")

        val users = restClient.get()
            .uri("$publicApiUrl/users")
            .retrieve()
            .body<List<User>>()

        return users!!
    }
}