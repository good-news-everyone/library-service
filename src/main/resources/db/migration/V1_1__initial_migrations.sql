create table authors
(
    id          bigserial primary key,
    first_name  text not null,
    last_name   text not null,
    birth_date  date not null,
    death_date  date,
    country     text not null,
    biography   text not null,
    description text not null
);

create table books
(
    id               bigserial primary key,
    author_id        bigint         not null references authors (id),
    title            text           not null,
    cover_type       text           not null,
    genre            text           not null,
    average_rating   decimal(11, 2) not null,
    page_count       int            not null,
    isbn             text           not null,
    publication_year int,
    created_at       timestamp      not null,
    updated_at       timestamp      not null
);

create table book_reviews
(
    id      bigserial primary key,
    book_id bigint not null references books (id),
    rating  int    not null
);
