package com.hometech.testref.helper

import com.hometech.testref.author.AuthorChangeRequest
import com.hometech.testref.author.AuthorEntity
import java.time.LocalDate

fun randomAuthor(
    id: Long = randomLong(from = 1),
    firstName: String = randomString(),
    lastName: String = randomString(),
    birthDate: LocalDate = LocalDate.now().minusYears(30),
    deathDate: LocalDate? = null,
    country: String = randomString(),
    biography: String = randomString(),
    description: String = randomString()
) = AuthorEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    birthDate = birthDate,
    deathDate = deathDate,
    country = country,
    biography = biography,
    description = description
)

fun randomAuthorChangeRequest(
    firstName: String = randomString(),
    lastName: String = randomString(),
    birthDate: LocalDate = LocalDate.now().minusYears(30),
    deathDate: LocalDate? = null,
    country: String = randomString(),
    biography: String = randomString(),
    description: String = randomString()
) = AuthorChangeRequest(
    firstName = firstName,
    lastName = lastName,
    birthDate = birthDate,
    deathDate = deathDate,
    country = country,
    biography = biography,
    description = description
)
