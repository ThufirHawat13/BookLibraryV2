package com.example.booklibraryv2.dto;

import java.util.List;
import lombok.Data;

@Data
public class LibraryUserDTO {

  private int id;
  private String name;
  private String surname;
  private List<BookDTO> bookList;
}
