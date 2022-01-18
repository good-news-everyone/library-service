package com.hometech.testref.book

import com.hometech.testref.author.AuthorView
import com.hometech.testref.author.toView
import java.math.BigDecimal
import java.time.Year

data class BookView(
    val id: Long,
    val author: AuthorView,
    val title: String,
    val averageRating: BigDecimal,
    val pageCount: Int,
    val isbn: String,
    val publicationYear: Year,
    val coverType: CoverType,
    val genre: Genre
)

data class BooksView(val books: List<BookView>)

data class BookChangeRequest(
    val authorId: Long,
    val title: String,
    val pageCount: Int,
    val isbn: String,
    val publicationYear: Year,
    val coverType: CoverType,
    val genre: Genre
)

data class BookRateChangeRequest(val rating: Int)

fun BookEntity.toView() = BookView(
    id = requireNotNull(id),
    author = this.author.toView(),
    title = this.title,
    averageRating = this.averageRating,
    pageCount = this.pageCount,
    isbn = this.isbn,
    publicationYear = this.publicationYear,
    coverType = this.coverType,
    genre = this.genre
)
