package com.app.todo.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface TodoJpaRepository : JpaRepository<TodoEntity, Int> {
}