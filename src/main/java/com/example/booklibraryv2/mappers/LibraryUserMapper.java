package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.services.BookService;
import com.example.booklibraryv2.services.LibraryUserService;
import java.util.Collections;
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

  public static LibraryUserDTO parseToLibraryUserDTO(LibraryUser libraryUser) {
  LibraryUserDTO libraryUserDTO = new LibraryUserDTO();

  libraryUserDTO.setName(libraryUser.getName());
  libraryUserDTO.setSurname(libraryUser.getSurname());
  libraryUserDTO.setBookList(Collections.emptyList());

  return libraryUserDTO;
  }

}
