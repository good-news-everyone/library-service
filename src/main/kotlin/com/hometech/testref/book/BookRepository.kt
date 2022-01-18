package com.hometech.testref.book

import com.hometech.testref.author.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<BookEntity, Long> {

    fun existsByAuthor(author: AuthorEntity): Boolean
    fun existsByIsbn(isbn: String): Boolean
}

@Repository
interface BookRatingRepository: JpaRepository<BookRateEntity, Long>
