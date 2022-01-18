package com.hometech.testref

import com.hometech.testref.author.AuthorEntity
import com.hometech.testref.author.AuthorView
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class AuthorTests : AbstractIntegrationTest() {

    @Test
    fun `should find author by id`() {
        TODO()
    }

    @Test
    fun `should not delete author if books exists`() {
        TODO()
    }

    private infix fun AuthorView.compareWith(expected: AuthorEntity) {
        this.id shouldBe expected.id
        this.firstName shouldBe expected.firstName
        this.lastName shouldBe expected.lastName
        this.birthDate shouldBe expected.birthDate
        this.deathDate shouldBe expected.deathDate
        this.country shouldBe expected.country
        this.biography shouldBe expected.biography
        this.description shouldBe expected.description
    }
}
