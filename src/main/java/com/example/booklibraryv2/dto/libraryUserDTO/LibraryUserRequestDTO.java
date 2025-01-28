package com.example.booklibraryv2.dto.libraryUserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUserRequestDTO {

  @NotBlank(message = "Name shouldn't be empty!")
  @Size(max = 30, message = "Length shouldn't be greater than 30!")
  private String name;
  @NotBlank(message = "Surname shouldn't be empty!")
  @Size(max = 30, message = "Length shouldn't be greater than 30!")
  private String surname;
}
