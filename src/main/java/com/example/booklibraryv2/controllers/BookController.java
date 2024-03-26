package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private final BookService bookService;

  @GetMapping
  public List<Book> index() {
    return bookService.getAll();
  }

  @GetMapping("/find/{searchQuery}")
  public List<Book> findByNameContains(@PathVariable String searchQuery) {
    return bookService.findByNameContains(searchQuery);
  }

  @PostMapping("/add")
  public ResponseEntity<HttpStatus> addNew(@RequestBody Book book) {
    bookService.save(book);

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @PatchMapping("/update")
  public ResponseEntity<HttpStatus> update(@RequestBody Book updatedBook) {
    bookService.update(updatedBook);

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @DeleteMapping("/remove")
  public ResponseEntity<HttpStatus> remove(@RequestBody Book book) {
    bookService.delete(book);

    return ResponseEntity.ok(HttpStatus.OK);
  }
}
