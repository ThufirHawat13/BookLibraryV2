package com.example.booklibraryv2.mappers;

import com.example.booklibraryv2.dto.BookDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.entities.LibraryUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryUserMapper {

  public static com.example.booklibraryv2.dto.LibraryUserDTO convertToLibraryUserDTO(
      LibraryUser libraryUser) {
  com.example.booklibraryv2.dto.LibraryUserDTO result = new com.example.booklibraryv2.dto.LibraryUserDTO();

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
      com.example.booklibraryv2.dto.LibraryUserDTO libraryUserDTO) {
    LibraryUser result = new LibraryUser();

    result.setId(libraryUserDTO.getId());
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
