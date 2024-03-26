package com.example.booklibraryv2.dto;

import com.example.booklibraryv2.entities.Book;
import java.util.List;
import lombok.Data;

@Data
public class LibraryUserDTO {

  private String name;
  private String surname;
  private List<BookDTO> bookList;
}
