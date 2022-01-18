package com.hometech.testref.book

import com.hometech.testref.author.AuthorEntity
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.Year
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "books")
class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "author_id")
    var author: AuthorEntity,

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL])
    var ratings: MutableList<BookRateEntity>,

    @Enumerated(EnumType.STRING) var coverType: CoverType,
    @Enumerated(EnumType.STRING) var genre: Genre,

    @Column(name = "average_rating", precision = 11, scale = 2)
    var averageRating: BigDecimal,

    var title: String,
    var pageCount: Int,
    var isbn: String,
    var publicationYear: Year,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)

@Entity
@Table(name = "book_reviews")
class BookRateEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    var book: BookEntity,

    var rating: Int
)

enum class CoverType { SOFT, HARD, GIFT }
enum class Genre {
    FANTASY,
    SCI_FI,
    ADVENTURE,
    ROMANCE,
    DETECTIVE,
    MYSTERY,
    HORROR,
    THRILLER,
    HISTORICAL
}
