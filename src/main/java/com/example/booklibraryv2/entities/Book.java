package com.example.booklibraryv2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "book")
@Data
public class Book {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "book_name")
  private String name;
  @Column(name = "author")
  private String author;
  @Column(name = "year_of_writing")
  private int yearOfWriting;
  @ManyToOne
  @JoinColumn(name = "book_holder",
  referencedColumnName = "id")
  private LibraryUser holder;
}
