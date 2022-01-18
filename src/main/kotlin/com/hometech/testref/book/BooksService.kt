package com.hometech.testref.book

import com.hometech.testref.author.AuthorRepository
import com.hometech.testref.common.BusinessException
import com.hometech.testref.common.findOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.Year

@Service
class BooksService(
    private val booksRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {

    fun findById(id: Long): BookEntity = booksRepository.findOrThrow(id)
    fun findAll(): List<BookEntity> = booksRepository.findAll()

    fun createBook(request: BookChangeRequest): BookEntity {
        request.validate()
        return BookEntity(
            author = authorRepository.findOrThrow(request.authorId),
            ratings = mutableListOf(),
            title = request.title,
            averageRating = BigDecimal.ZERO,
            pageCount = request.pageCount,
            isbn = request.isbn,
            publicationYear = request.publicationYear,
            coverType = request.coverType,
            genre = request.genre,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ).apply { booksRepository.save(this) }
    }

    fun updateBook(id: Long, request: BookChangeRequest): BookEntity {
        request.validate()
        return booksRepository
            .findOrThrow(id)
            .apply {
                this.author = authorRepository.findOrThrow(request.authorId)
                this.pageCount = request.pageCount
                this.isbn = request.isbn
                this.publicationYear = request.publicationYear
                this.coverType = request.coverType
                this.genre = request.genre
                this.updatedAt = LocalDateTime.now()

                booksRepository.save(this)
            }
    }

    fun deleteBook(id: Long) {
        booksRepository
            .findOrThrow(id)
            .also {
                booksRepository.delete(it)
            }
    }

    @Transactional
    fun addReview(bookId: Long, request: BookRateChangeRequest): BookEntity {
        request.validate()
        return booksRepository
            .findOrThrow(bookId)
            .apply {
                this.ratings += BookRateEntity(book = this, rating = request.rating)
                this.averageRating = ratings.sumOf { it.rating }.toBigDecimal().divide(ratings.size.toBigDecimal(), 2 ,RoundingMode.HALF_UP)
            }
    }
    
    private fun BookRateChangeRequest.validate() {
        if (rating < 1) throw BusinessException("Рейтинг не может быть менее 1")
        if (rating > 5) throw BusinessException("Рейтинг не может быть более 5")
    }
    
    private fun BookChangeRequest.validate() {
        if (this.publicationYear.isAfter(Year.now())) 
            throw BusinessException("Год выпуска книги не может быть позже текущего года")
        if (booksRepository.existsByIsbn(this.isbn))
            throw BusinessException("Книга с таким ISBN уже существует")
    }
}
