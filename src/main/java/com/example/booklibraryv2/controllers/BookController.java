package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.dto.BookDTO;
import com.example.booklibraryv2.mappers.BookMapper;
import com.example.booklibraryv2.services.BookService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @GetMapping
  public List<BookDTO> getAll() {
    return bookService.getAll().stream()
        .map(BookMapper::convertToBookDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public BookDTO getById(@PathVariable Integer id) {
    return BookMapper.convertToBookDTO(bookService.findById(id));
  }

  @GetMapping("/find/{searchQuery}")
  public List<BookDTO> findByNameContains(@PathVariable String searchQuery) {
    return bookService.findByNameContains(searchQuery).stream()
        .map(BookMapper::convertToBookDTO)
        .collect(Collectors.toList());
  }

  @PostMapping()
  public ResponseEntity<HttpStatus> create(@RequestBody @Valid BookDTO bookDTO) {
    bookService.save(BookMapper.convertToBook(bookDTO));

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }

  @PatchMapping()
  public ResponseEntity<HttpStatus> update(@RequestBody BookDTO updatedBook) {
    bookService.update(BookMapper.convertToBook(updatedBook));

    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

  @DeleteMapping()
  public ResponseEntity<HttpStatus> delete(@RequestBody BookDTO bookDTO) {
    bookService.delete(BookMapper.convertToBook(bookDTO));

    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }
}
