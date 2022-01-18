package com.hometech.testref.helper

import com.hometech.testref.author.AuthorEntity
import com.hometech.testref.book.BookChangeRequest
import com.hometech.testref.book.BookEntity
import com.hometech.testref.book.CoverType
import com.hometech.testref.book.Genre
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.Year

fun randomBook(
    author: AuthorEntity,
    id: Long = randomLong(from = 1),
    title: String = randomString(),
    rating: BigDecimal = BigDecimal.ZERO,
    pageCount: Int = randomInt(from = 10),
    isbn: String = randomString(),
    publicationYear: Year = Year.of(randomInt(1900, 2021)),
    coverType: CoverType = CoverType.values().random(),
    genre: Genre = Genre.values().random()
) = BookEntity(
    id = id,
    author = author,
    title = title,
    averageRating = rating,
    pageCount = pageCount,
    isbn = isbn,
    publicationYear = publicationYear,
    coverType = coverType,
    genre = genre,
    ratings = mutableListOf(),
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now()
)

fun randomBookChangeRequest(
    authorId: Long,
    title: String = randomString(),
    pageCount: Int = randomInt(),
    isbn: String = randomString(),
    publicationYear: Year = Year.of(randomInt(1900, 2021)),
    coverType: CoverType = CoverType.values().random(),
    genre: Genre = Genre.values().random()
) = BookChangeRequest(
    authorId = authorId,
    title = title,
    pageCount = pageCount,
    isbn = isbn,
    publicationYear = publicationYear,
    coverType = coverType,
    genre = genre
)
