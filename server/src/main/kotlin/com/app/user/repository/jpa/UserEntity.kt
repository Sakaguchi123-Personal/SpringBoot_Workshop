package com.app.user.repository.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @GeneratedValue @Id val id: Int,

    @Column(name = "nickname")
    val name: String,
    val email: String) {
}