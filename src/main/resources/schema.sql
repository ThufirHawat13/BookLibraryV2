CREATE TABLE library_user
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL
);

INSERT INTO library_user (name, surname)
VALUES ('John', 'Doe');

CREATE TABLE book
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_name       VARCHAR(50) UNIQUE NOT NULL,
    author          VARCHAR(50)        NOT NULL,
    year_of_writing NUMBER(4)          NOT NULL,
    book_holder_id  BIGINT             REFERENCES library_user (id) ON DELETE SET NULL
);

CREATE TABLE librarian
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role     VARCHAR(16) NOT NULL
);

INSERT INTO book (book_name, author, year_of_writing)
VALUES ('Java Core Guide', 'Frank Foe', 2022);

