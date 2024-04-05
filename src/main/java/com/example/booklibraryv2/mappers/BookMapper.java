package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.BookDTO;
import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.services.BookService;
import com.example.booklibraryv2.services.LibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class BookMapper {


  public static BookDTO convertToBookDTO(Book book) {
    BookDTO result = new BookDTO();

    result.setName(book.getName());
    result.setAuthor(book.getAuthor());
    result.setYearOfWriting(book.getYearOfWriting());

    LibraryUser holder;
    if ((holder = book.getHolder()) != null) {
      result.setHolder(LibraryUserMapper.convertToLibraryUserDTO(holder));
    }

    return result;
  }

  public static Book convertToBook(BookDTO bookDTO) {
    Book result = new Book();

    result.setName(bookDTO.getName());
    result.setAuthor(bookDTO.getAuthor());
    result.setYearOfWriting(bookDTO.getYearOfWriting());

    LibraryUserDTO holderDTO;
    if ((holderDTO = bookDTO.getHolder()) != null) {
      result.setHolder(LibraryUserMapper.convertToLibraryUser(holderDTO));
    }

    return result;
  }
}
