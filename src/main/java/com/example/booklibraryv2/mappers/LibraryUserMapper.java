package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserResponseDTO;
import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserRequestDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryUserMapper {

  public static LibraryUserResponseDTO convertToLibraryUserDTO(LibraryUser libraryUser) {
    LibraryUserResponseDTO result = new LibraryUserResponseDTO();

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

  public static LibraryUser convertToLibraryUser(LibraryUserRequestDTO libraryUserRequestDTO) {
    LibraryUser result = new LibraryUser();

    result.setName(libraryUserRequestDTO.getName());
    result.setSurname(libraryUserRequestDTO.getSurname());

    return result;
  }

}
