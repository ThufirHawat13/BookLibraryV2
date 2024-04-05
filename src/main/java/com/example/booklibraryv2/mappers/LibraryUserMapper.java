package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.BookDTO;
import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.services.BookService;
import com.example.booklibraryv2.services.LibraryUserService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class LibraryUserMapper {

  public static LibraryUserDTO convertToLibraryUserDTO(LibraryUser libraryUser) {
  LibraryUserDTO result = new LibraryUserDTO();

  result.setName(libraryUser.getName());
  result.setSurname(libraryUser.getSurname());

    List<Book> bookList;
  if ((bookList = libraryUser.getBookList()) != null) {
    result.setBookList(bookList.stream()
        .map(BookMapper::convertToBookDTO)
        .collect(Collectors.toList()));
  }

  return result;
  }

  public static LibraryUser convertToLibraryUser(LibraryUserDTO libraryUserDTO) {
    LibraryUser result = new LibraryUser();

    result.setName(libraryUserDTO.getName());
    result.setSurname(libraryUserDTO.getSurname());

    List<BookDTO> bookDTOList;
    if ((bookDTOList = libraryUserDTO.getBookList()) != null) {
      result.setBookList(bookDTOList.stream()
          .map(BookMapper::convertToBook)
          .collect(Collectors.toList()));
    }

    return result;
  }

}
