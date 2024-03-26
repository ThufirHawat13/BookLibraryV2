package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.BookDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.services.BookService;
import com.example.booklibraryv2.services.LibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

  @Autowired
  private final BookService bookService;
  @Autowired
  private final LibraryUserService libraryUserService;

  public static BookDTO convertToBookDTO(Book book) {
    BookDTO result = new BookDTO();

    result.setName(book.getName());
    result.setAuthor(book.getAuthor());
    result.setYearOfWriting(book.getYearOfWriting());
    result.setHolder(null);

    return result;
  }
}
