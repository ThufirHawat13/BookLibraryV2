package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryUserMapper {

  public static LibraryUserDTO convertToLibraryUserDTO(
      LibraryUser libraryUser) {
    LibraryUserDTO result = new LibraryUserDTO();

    result.setId(libraryUser.getId());
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

  public static LibraryUser convertToLibraryUser(
      LibraryUserDTO libraryUserDTO) {
    LibraryUser result = new LibraryUser();

    result.setId(libraryUserDTO.getId());
    result.setName(libraryUserDTO.getName());
    result.setSurname(libraryUserDTO.getSurname());

    return result;
  }

}
