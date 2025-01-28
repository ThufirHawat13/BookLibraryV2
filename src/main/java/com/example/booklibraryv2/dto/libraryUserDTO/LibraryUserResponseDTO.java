package com.example.booklibraryv2.dto.libraryUserDTO;

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
public class LibraryUserResponseDTO {

  private Long id;
  private String name;
  private String surname;
  private List<BookResponseDTO> bookList;
}
