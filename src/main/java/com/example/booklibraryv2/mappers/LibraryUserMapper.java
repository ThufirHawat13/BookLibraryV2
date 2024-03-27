package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.services.BookService;
import com.example.booklibraryv2.services.LibraryUserService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryUserMapper {

  @Autowired
  private final LibraryUserService libraryUserService;
  @Autowired
  private final BookService bookService;

  public LibraryUserDTO convertToLibraryUserDTO(LibraryUser libraryUser) {
  LibraryUserDTO libraryUserDTO = new LibraryUserDTO();

  libraryUserDTO.setName(libraryUser.getName());
  libraryUserDTO.setSurname(libraryUser.getSurname());

    List<Book> bookList;
  if ((bookList = libraryUser.getBookList()) != null) {
    libraryUser.setBookList(bookList);
  }

  return libraryUserDTO;
  }

}
