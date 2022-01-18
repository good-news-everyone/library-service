package com.hometech.testref

import com.hometech.testref.author.AuthorEntity
import com.hometech.testref.book.BookEntity
import com.hometech.testref.book.BookRateChangeRequest
import com.hometech.testref.book.BookView
import com.hometech.testref.book.BooksView
import com.hometech.testref.helper.randomAuthor
import com.hometech.testref.helper.randomBook
import com.hometech.testref.helper.randomBookChangeRequest
import com.hometech.testref.helper.shouldBeEqualsComparing
import com.hometech.testref.helper.shouldBeIgnoreMillis
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.Year

class BookTests : AbstractIntegrationTest() {

    @Test
    fun `should create book`() {
        val author = randomAuthor().save<AuthorEntity>()
        val changeRequest = randomBookChangeRequest(authorId = author.id!!)
        val response = mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isOk() } }
            .andReturn().asObject<BookView>()
        repositoryHolder.bookRepository.findAll().first().also {
            response compareWith it
            it.createdAt shouldBeIgnoreMillis LocalDateTime.now()
            it.updatedAt shouldBeIgnoreMillis LocalDateTime.now()
        }
    }

    @Test
    fun `should not create book if year is after current`() {
        val author = randomAuthor().save<AuthorEntity>()
        val changeRequest = randomBookChangeRequest(authorId = author.id!!, publicationYear = Year.now().plusYears(1))
        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isConflict() } }
    }

    @Test
    fun `should not create book if author not exists`() {
        val changeRequest = randomBookChangeRequest(authorId = 1)
        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should update book`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).apply { this.createdAt = LocalDateTime.now().minusDays(1) }.save<BookEntity>()
        val changeRequest = randomBookChangeRequest(authorId = author.id!!)
        val response = mockMvc.put("/books/${book.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isOk() } }
            .andReturn().asObject<BookView>()
        repositoryHolder.bookRepository.findAll().first().also {
            response compareWith it
            it.createdAt shouldBeIgnoreMillis LocalDateTime.now().minusDays(1)
            it.updatedAt shouldBeIgnoreMillis LocalDateTime.now()
        }
    }

    @Test
    fun `should not update book if year is after current`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).save<BookEntity>()
        val changeRequest = randomBookChangeRequest(authorId = author.id!!, publicationYear = Year.now().plusYears(1))
        mockMvc.put("/books/${book.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isConflict() } }
    }

    @Test
    fun `should not update book if author not exists`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).save<BookEntity>()
        val changeRequest = randomBookChangeRequest(authorId = 1)
        mockMvc.put("/books/${book.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should not update book if book not exists`() {
        val author = randomAuthor().save<AuthorEntity>()
        val changeRequest = randomBookChangeRequest(authorId = author.id!!)
        mockMvc.put("/books/1") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should delete book`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).save<BookEntity>()
        mockMvc.delete("/books/${book.id}")
            .andExpect { status { isOk() } }
        repositoryHolder.bookRepository.findAll().shouldBeEmpty()
    }

    @Test
    fun `should find book by id`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).save<BookEntity>()
        val response = mockMvc.get("/books/${book.id}")
            .andExpect { status { isOk() } }
            .andReturn().asObject<BookView>()
        repositoryHolder.bookRepository.findAll().first().also {
            response compareWith it
            it.createdAt shouldBeIgnoreMillis LocalDateTime.now()
            it.updatedAt shouldBeIgnoreMillis LocalDateTime.now()
        }
    }

    @Test
    fun `should find all books`() {
        randomAuthor().save<AuthorEntity>().also {
            randomBook(author = it).save<BookEntity>()
        }
        val response = mockMvc.get("/books")
            .andExpect { status { isOk() } }
            .andReturn().asObject<BooksView>()
        repositoryHolder.bookRepository.findAll().first().also {
            response.books.size shouldBe 1
            response.books.first() compareWith it
            it.createdAt shouldBeIgnoreMillis LocalDateTime.now()
            it.updatedAt shouldBeIgnoreMillis LocalDateTime.now()
        }
    }

    @Test
    fun `should leave review on book`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).apply { this.createdAt = LocalDateTime.now().minusDays(1) }.save<BookEntity>()
        val changeRequest = BookRateChangeRequest(2)
        val response = mockMvc.post("/books/${book.id}/rate") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isOk() } }
            .andReturn().asObject<BookView>()
        repositoryHolder.bookRepository.findAll().first().also {
            response.averageRating shouldBeEqualsComparing BigDecimal(2)
        }
    }

    @Test
    fun `should not leave review on book if book not exists`() {
        val changeRequest = BookRateChangeRequest(2)
        mockMvc.post("/books/1/rate") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should not leave review on book if rate lt 1`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).apply { this.createdAt = LocalDateTime.now().minusDays(1) }.save<BookEntity>()
        val changeRequest = BookRateChangeRequest(0)
        mockMvc.post("/books/${book.id}/rate") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isConflict() } }
    }

    @Test
    fun `should not leave review on book if rate gt 5`() {
        val author = randomAuthor().save<AuthorEntity>()
        val book = randomBook(author = author).apply { this.createdAt = LocalDateTime.now().minusDays(1) }.save<BookEntity>()
        val changeRequest = BookRateChangeRequest(6)
        mockMvc.post("/books/${book.id}/rate") {
            contentType = MediaType.APPLICATION_JSON
            content = changeRequest.asJson()
        }
            .andExpect { status { isConflict() } }
    }

    private infix fun BookView.compareWith(expected: BookEntity) {
        this.id shouldBe expected.id

        this.author.id shouldBe expected.author.id
        this.author.firstName shouldBe expected.author.firstName
        this.author.lastName shouldBe expected.author.lastName
        this.author.birthDate shouldBe expected.author.birthDate
        this.author.deathDate shouldBe expected.author.deathDate
        this.author.country shouldBe expected.author.country
        this.author.biography shouldBe expected.author.biography
        this.author.description shouldBe expected.author.description

        this.title shouldBe expected.title
        this.averageRating shouldBeEqualsComparing expected.averageRating
        this.pageCount shouldBe expected.pageCount
        this.isbn shouldBe expected.isbn
        this.publicationYear shouldBe expected.publicationYear
        this.coverType shouldBe expected.coverType
        this.genre shouldBe expected.genre
    }
}
