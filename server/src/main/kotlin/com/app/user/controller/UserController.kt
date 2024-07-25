package com.app.user.controller

import com.app.user.client.UserClient
import com.app.user.model.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userClient: UserClient) {

    @GetMapping("/api/v1/users")
    fun getUsers(): List<User> {
        return userClient.users()
    }
}