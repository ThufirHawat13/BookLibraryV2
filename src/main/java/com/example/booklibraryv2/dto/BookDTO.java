package com.example.booklibraryv2.dto;

import lombok.Data;

@Data
public class BookDTO {
  private int id;
  private String name;
  private String author;
  private int yearOfWriting;
  private LibraryUserDTO holder;
}
