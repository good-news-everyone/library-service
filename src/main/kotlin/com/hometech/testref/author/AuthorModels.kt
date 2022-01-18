package com.hometech.testref.author

import java.time.LocalDate

data class AuthorView(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate?,
    val country: String,
    val biography: String,
    val description: String
)

data class AuthorsView(val authors: List<AuthorView>)

data class AuthorChangeRequest(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate?,
    val country: String,
    val biography: String,
    val description: String
)

fun AuthorEntity.toView() = AuthorView(
    id = requireNotNull(this.id),
    firstName = this.firstName,
    lastName = this.lastName,
    birthDate = this.birthDate,
    deathDate = this.deathDate,
    country = this.country,
    biography = this.biography,
    description = this.description
)
