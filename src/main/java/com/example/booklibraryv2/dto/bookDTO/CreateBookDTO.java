package com.example.booklibraryv2.dto.bookDTO;

import com.example.booklibraryv2.dto.LibraryUserDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookDTO {

  @NotBlank(message = "Name shouldn't be empty!")
  @Size(max = 200, message = "Length shouldn't be greater than 200!")
  private String name;
  @NotBlank(message = "Author designation shouldn't be empty!")
  @Size(max = 200, message = "Length shouldn't be greater than 200!")
  private String author;
  @NotNull(message = "Year of writing shouldn't be empty!")
  @Min(value = 0,
      message = "Year of writing shouldn't be lower than 0!")
  @Max(value = 2024,
      message = "Year of writing shouldn't be greater than 2024!")
  private Integer yearOfWriting;
}
