package com.example.booklibraryv2.dto.bookDTO;

import com.example.booklibraryv2.dto.LibraryUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  private Long id;
  private String name;
  private String author;
  private Integer yearOfWriting;
  private LibraryUserDTO holder;
}
