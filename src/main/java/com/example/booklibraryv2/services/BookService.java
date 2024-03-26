package com.example.booklibraryv2.services;

import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.repositories.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

  @Autowired
  private final BookRepository bookRepository;

  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  public List<Book> findByNameContains(String searchQuery) {
    return bookRepository.findByNameContains(searchQuery);
  }

  @Transactional
  public void save(Book book) {
    bookRepository.save(book);
  }

  @Transactional
  public void update(Book updatedBook) {
    Book book = bookRepository.findById(updatedBook.getId()).orElse(null);

    book.setName(updatedBook.getName());
    book.setAuthor(updatedBook.getAuthor());
    book.setHolder(updatedBook.getHolder());
    book.setYearOfWriting(updatedBook.getYearOfWriting());
  }

  @Transactional
  public void delete(Book book) {
    bookRepository.delete(book);
  }
}
