package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.bookDTO.BookRequestDTO;
import com.example.booklibraryv2.dto.bookDTO.BookResponseDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookMapper {

  public static BookResponseDTO convertToBookDTO(Book book) {
    BookResponseDTO result = new BookResponseDTO();

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

  public static Book convertToBook(BookRequestDTO bookRequestDTO) {
    Book result = new Book();

    result.setName(bookRequestDTO.getName());
    result.setAuthor(bookRequestDTO.getAuthor());
    result.setYearOfWriting(bookRequestDTO.getYearOfWriting());

    return result;
  }
}
