package com.example.booklibraryv2.services;

import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.repositories.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;

  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  public Book findById(Long id) throws ServiceException {
    return bookRepository.findById(id)
        .orElseThrow(() -> new ServiceException("Book with id = %d isn't found!".formatted(id)));
  }

  public List<Book> findByNameContains(String searchQuery) {
    return bookRepository.findByNameContains(searchQuery);
  }

  @Transactional
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  @Transactional
  public Book update(Book updatedBook) throws ServiceException {

    if (bookRepository.existsById(updatedBook.getId())) {
      return bookRepository.save(updatedBook);
    } else {
      throw new ServiceException(
          "Book with id=%s isn't exists!".formatted(updatedBook.getId()));
    }
  }

  @Transactional
  public Book delete(Long id) throws ServiceException {
    Book bookForDelete = bookRepository.findById(id)
        .orElseThrow(() -> new ServiceException(
            "Book with id=%s isn't exists!".formatted(id)));

    bookRepository.deleteById(id);

    return bookForDelete;
  }
}
