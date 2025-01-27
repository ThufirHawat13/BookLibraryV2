package com.example.booklibraryv2.dto.libraryUserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUserRequestDTO {

  private String name;
  private String surname;
}
