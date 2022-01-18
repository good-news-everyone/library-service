package com.hometech.testref.author

import com.hometech.testref.book.BookRepository
import com.hometech.testref.common.BusinessException
import com.hometech.testref.common.findOrThrow
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) {

    fun findById(id: Long): AuthorEntity = authorRepository.findOrThrow(id)
    fun findAll(): List<AuthorEntity> = authorRepository.findAll()

    fun createAuthor(request: AuthorChangeRequest): AuthorEntity {
        request.validate()
        return AuthorEntity(
            firstName = request.firstName,
            lastName = request.lastName,
            birthDate = request.birthDate,
            deathDate = request.deathDate,
            country = request.country,
            biography = request.biography,
            description = request.description
        ).let { authorRepository.save(it) }
    }

    fun updateAuthor(id: Long, request: AuthorChangeRequest): AuthorEntity {
        request.validate()
        return authorRepository
            .findOrThrow(id)
            .apply {
                this.firstName = request.firstName
                this.lastName = request.lastName
                this.birthDate = request.birthDate
                this.deathDate = request.deathDate
                this.country = request.country
                this.biography = request.biography
                this.description = request.description

                authorRepository.save(this)
            }
    }

    fun deleteAuthor(id: Long) {
        authorRepository
            .findOrThrow(id)
            .also { if (bookRepository.existsByAuthor(it)) throw BusinessException("Невозможно удалить автора, т.к. у автора есть книги") }
            .apply { authorRepository.delete(this) }
    }

    private fun AuthorChangeRequest.validate() {
        if (birthDate >= LocalDate.now()) throw BusinessException("Дата рождения не может быть позднее текущего дня")
        if (deathDate != null && deathDate >= LocalDate.now()) throw BusinessException("Дата смерти не может быть позднее текущего дня")
        if (birthDate == deathDate || birthDate > deathDate) throw BusinessException("Дата смерти не может быть ранее даты рождения")
    }
}
