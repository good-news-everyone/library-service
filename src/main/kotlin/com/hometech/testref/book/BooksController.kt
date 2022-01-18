package com.hometech.testref.book

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BooksController(private val service: BooksService) {

    @PostMapping
    fun createBook(@RequestBody request: BookChangeRequest): BookView {
        return service.createBook(request).toView()
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable id: Long,
        @RequestBody request: BookChangeRequest
    ): BookView {
        return service.updateBook(id, request).toView()
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long) {
        service.deleteBook(id)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): BookView {
        return service.findById(id).toView()
    }

    @GetMapping
    fun findByAll(): BooksView {
        return service.findAll().map { it.toView() }.let { BooksView(it) }
    }

    @PostMapping("/{id}/rate")
    fun rateBook(@PathVariable id: Long, @RequestBody rateUpdate: BookRateChangeRequest): BookView {
        return service.addReview(id, rateUpdate).toView()
    }
}
