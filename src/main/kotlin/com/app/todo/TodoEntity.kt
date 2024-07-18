package com.app.todo

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

//マイグレの代わり(jpa) jdbctemplateだとマイグレがいる
@Entity
class TodoEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Int,
    val name: String,
    val finished: Boolean) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TodoEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (finished != other.finished) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + finished.hashCode()
        return result
    }
}