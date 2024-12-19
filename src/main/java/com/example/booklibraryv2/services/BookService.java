package com.example.booklibraryv2.services;

import com.example.booklibraryv2.dto.bookDTO.UpdateBookDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookRepository bookRepository;


  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  public Book findById(Long id) throws ServiceException {
    return findByIdOrThrow(id);
  }

  public List<Book> findByNameContains(String searchQuery) {
    return bookRepository.findByNameContains(searchQuery);
  }

  public Book save(Book book) {
    Book savedBook = bookRepository.save(book);
    log.info("saved book: {}", savedBook);

    return savedBook;
  }

  @Transactional
  public Book update(Long id, UpdateBookDTO updateBookDTO) throws ServiceException {
    Book updatedBook = updateFields(findByIdOrThrow(id), updateBookDTO);
    log.info("updated book: {}", updatedBook);

    return updatedBook;
  }

  private Book updateFields(Book bookForUpdate, UpdateBookDTO updateBookDTO) {
    Optional.ofNullable(updateBookDTO.getName())
        .ifPresent(bookForUpdate::setName);
    Optional.ofNullable(updateBookDTO.getAuthor())
        .ifPresent(bookForUpdate::setAuthor);
    Optional.ofNullable(updateBookDTO.getYearOfWriting())
        .ifPresent(bookForUpdate::setYearOfWriting);

    return bookForUpdate;
  }

  @Transactional
  public void delete(Long id) throws ServiceException {
    Book bookForDelete = findByIdOrThrow(id);
    bookRepository.deleteById(id);
    log.info("deleted book: {}", bookForDelete);
  }

  private Book findByIdOrThrow(Long id) throws ServiceException {
    return bookRepository.findById(id)
        .orElseThrow(() -> new ServiceException(
            "Book with id=%s isn't exists!".formatted(id)));
  }
}
