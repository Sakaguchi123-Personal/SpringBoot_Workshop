package com.app.todo.controller

import jakarta.validation.constraints.NotEmpty

data class TodoUpdateRequest(
    @field:NotEmpty val name: String,
    val finished: Boolean)
