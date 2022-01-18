package com.hometech.testref.author

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {

    @PostMapping
    fun createAuthor(@RequestBody request: AuthorChangeRequest): AuthorView {
        return service.createAuthor(request).toView()
    }

    @PutMapping("/{id}")
    fun updateAuthor(
        @PathVariable id: Long,
        @RequestBody request: AuthorChangeRequest
    ): AuthorView = service.updateAuthor(id, request).toView()

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: Long) {
        service.deleteAuthor(id)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): AuthorView = service.findById(id).toView()

    @GetMapping
    fun findByAll(): AuthorsView = service.findAll().map { it.toView() }.let { AuthorsView(it) }
}
