package com.example.booklibraryv2.dto;

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
  private String name;
  private String author;
  private int yearOfWriting;
  private LibraryUserDTO holder;
}
