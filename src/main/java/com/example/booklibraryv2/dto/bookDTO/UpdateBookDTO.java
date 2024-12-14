package com.example.booklibraryv2.dto.bookDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookDTO {

  @Size(max = 200, message = "Length shouldn't be greater than 200!")
  private String name;
  @Size(max = 200, message = "Length shouldn't be greater than 200!")
  private String author;
  @Min(value = 0,
      message = "Year of writing shouldn't be lower than 0!")
  @Max(value = 2024,
      message = "Year of writing shouldn't be greater than 2024!")
  private Integer yearOfWriting;
}
