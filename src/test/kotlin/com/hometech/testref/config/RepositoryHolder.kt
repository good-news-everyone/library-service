package com.hometech.testref.config

import com.hometech.testref.author.AuthorEntity
import com.hometech.testref.author.AuthorRepository
import com.hometech.testref.book.BookEntity
import com.hometech.testref.book.BookRateEntity
import com.hometech.testref.book.BookRatingRepository
import com.hometech.testref.book.BookRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class RepositoryHolder(
    val bookRepository: BookRepository,
    val authorRepository: AuthorRepository,
    val bookRatingRepository: BookRatingRepository
) {

    fun save(instance: Any): Any {
        return when (instance) {
            is BookRateEntity -> bookRatingRepository.save(instance)
            is BookEntity -> bookRepository.save(instance)
            is AuthorEntity -> authorRepository.save(instance)
            else -> throw IllegalArgumentException("No repository found for given type")
        }
    }

    fun dropData() {
        bookRatingRepository.deleteAll()
        bookRepository.deleteAll()
        authorRepository.deleteAll()
    }
}
