package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.dto.bookDTO.BookRequest;
import com.example.booklibraryv2.dto.bookDTO.BookResponse;
import com.example.booklibraryv2.dto.validationGroups.CreateGroup;
import com.example.booklibraryv2.dto.validationGroups.UpdateGroup;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.mappers.BookMapper;
import com.example.booklibraryv2.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
  //TODO add swagger

  private final BookService bookService;
  private final BookMapper bookMapper;


  @GetMapping
  public List<BookResponse> getAll() {
    return bookMapper.toResponses(
        bookService.getAll());
  }

  @GetMapping("/{id}")
  public BookResponse getById(
      @PathVariable("id") Long id) throws ServiceException {
    return bookMapper.toResponse(
        bookService.findById(id));
  }

  @GetMapping("/find/{searchQuery}")
  public List<BookResponse> findByNameContains(
      @PathVariable("searchQuery") String searchQuery) {
    return bookMapper.toResponses(
        bookService.findByNameContains(searchQuery));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponse create(
      @RequestBody @Validated(CreateGroup.class) BookRequest newBook) {
    return bookMapper.toResponse(
        bookService.save(bookMapper.toEntity(newBook)));
  }

  @PatchMapping("/{id}")
  public BookResponse update(
      @PathVariable("id") Long id,
      @RequestBody @Validated(UpdateGroup.class) BookRequest updatedFields)
      throws ServiceException {
    return bookMapper.toResponse(
        bookService.update(id, updatedFields));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable("id") Long id) throws ServiceException {
    bookService.delete(id);
  }
}
