package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.bookDTO.CreateBookDTO;
import com.example.booklibraryv2.dto.bookDTO.BookDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookMapper {

  public static BookDTO convertToBookDTO(Book book) {
    BookDTO result = new BookDTO();

    result.setId(book.getId());
    result.setName(book.getName());
    result.setAuthor(book.getAuthor());
    result.setYearOfWriting(book.getYearOfWriting());

    LibraryUser holder;
    if ((holder = book.getHolder()) != null) {
      result.setHolder(LibraryUserMapper.convertToLibraryUserDTO(holder));
    }

    return result;
  }

  public static Book convertToBook(CreateBookDTO createBookDTO) {
    Book result = new Book();

    result.setName(createBookDTO.getName());
    result.setAuthor(createBookDTO.getAuthor());
    result.setYearOfWriting(createBookDTO.getYearOfWriting());

    return result;
  }
}
