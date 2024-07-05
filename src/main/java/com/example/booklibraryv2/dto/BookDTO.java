package com.example.booklibraryv2.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  private Integer id;
  @NotBlank
  @Size(min = 1, max = 200, message = "Length shouldn't be lower than 1 and greater than 200")
  private String name;
  @NotBlank
  @Pattern(regexp = "^[A-Z]\\w*(\\s[A-Z]\\.[A-Z]\\.|$|\\s[A-Z]\\.)",
      message = "Authors designation isn't valid!(Examples: 'Petrov A.V.', 'Lin X.', 'Euclid'")
  @Size(min = 1, max = 200, message = "Length shouldn't be lower than 1 and greater than 200")
  private String author;
  @Min(value = 0,
      message = "Year of writing shouldn't be lower than 0!")
  @Max(value = 2024,
      message = "Year of writing shouldn't be greater than 2024!")
  private Integer yearOfWriting;
  private LibraryUserDTO holder;
}
