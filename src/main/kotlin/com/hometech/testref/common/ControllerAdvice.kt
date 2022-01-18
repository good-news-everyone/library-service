package com.hometech.testref.common

import mu.KotlinLogging
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.sql.SQLException

private val log = KotlinLogging.logger { }

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(SQLException::class, Exception::class)
    fun handleDataWriteException(exception: Exception): ResponseEntity<out BaseError> {
        val error = if (exception is SQLException)
            BaseError(description = "Ошибка при работе с базой данных.")
        else
            BaseError(description = "Внутренняя ошибка сервера.")
        log.error(exception) { exception.message }
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [DuplicateKeyException::class, BusinessException::class])
    fun handleDuplicateException(exception: RuntimeException): ResponseEntity<out BaseError> {
        log.error(exception) { exception.message }
        return ResponseEntity(
            BaseError(description = exception.message ?: ""),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(value = [NoSuchElementException::class, NotFoundException::class])
    fun handleCommonNotFoundException(exception: RuntimeException): ResponseEntity<out BaseError> {
        log.error(exception) { exception.message }
        return ResponseEntity(
            BaseError(description = exception.message ?: "Элемент не найден в коллекции."),
            HttpStatus.NOT_FOUND
        )
    }
}

data class BaseError(val description: String)
