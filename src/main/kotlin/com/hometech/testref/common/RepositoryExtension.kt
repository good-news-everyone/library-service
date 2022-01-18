package com.hometech.testref.common

import org.springframework.data.jpa.repository.JpaRepository

inline fun <reified T> JpaRepository<T, Long>.findOrThrow(id: Long): T {
    return this.findById(id).orElseThrow { NotFoundException("Запись типа '${T::class.java.simpleName}' с id = $id не найдена") }
}
