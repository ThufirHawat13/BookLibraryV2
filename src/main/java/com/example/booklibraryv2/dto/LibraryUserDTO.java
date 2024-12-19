package com.example.booklibraryv2.dto;

import com.example.booklibraryv2.dto.bookDTO.BookResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

  private Long id;
  @NotBlank(message = "Name shouldn't be empty!")
  @Size(max = 30, message = "Length shouldn't be greater than 30!")
  private String name;
  @NotBlank(message = "Surname shouldn't be empty!")
  @Size(max = 30, message = "Length shouldn't be greater than 30!")
  private String surname;
  private List<BookResponseDTO> bookList;
}
