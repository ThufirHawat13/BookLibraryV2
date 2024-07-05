package com.example.booklibraryv2.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUserDTO {
  private Integer id;
  private String name;
  private String surname;
  private List<BookDTO> bookList;
}
