package com.example.booklibraryv2.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.booklibraryv2.dto.BookDTO;
import com.example.booklibraryv2.entities.Book;
import com.example.booklibraryv2.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(BookController.class)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private BookService bookService;
  private final String ENDPOINT = "/books";

  //TODO Добавить негтив кейсы после добавления валидации

  @Test
  void getAllShouldReturnBookDtoList() throws Exception {
    when(bookService.getAll())
        .thenReturn(List.of(getTestBook()));

    mvc.perform(get(ENDPOINT)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[0].name").value("Book"))
        .andExpect(jsonPath("$[0].author").value("Author"))
        .andExpect(jsonPath("$[0].holder").isEmpty())
        .andExpect(jsonPath("$[0].yearOfWriting").value("1111"));

    verify(bookService, times(1))
        .getAll();
  }

  @Test
  void getByIdShouldReturnBookDto() throws Exception {
    when(bookService.findById(1))
        .thenReturn(getTestBook());

    mvc.perform(get(ENDPOINT + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("Book"))
        .andExpect(jsonPath("$.author").value("Author"))
        .andExpect(jsonPath("$.holder").isEmpty())
        .andExpect(jsonPath("$.yearOfWriting").value("1111"));

    verify(bookService, times(1))
        .findById(1);
  }

  @Test
  void findByNameContainsShouldReturnBookDtoList() throws Exception {
    when(bookService.findByNameContains("Book"))
        .thenReturn(List.of(getTestBook()));

    mvc.perform(get(ENDPOINT + "/find/Book")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[0].name").value("Book"))
        .andExpect(jsonPath("$[0].author").value("Author"))
        .andExpect(jsonPath("$[0].holder").isEmpty())
        .andExpect(jsonPath("$[0].yearOfWriting").value("1111"));

    verify(bookService, times(1))
        .findByNameContains("Book");
  }

  @Test
  void createShouldCreateSuccessful() throws Exception {
    mvc.perform(post(ENDPOINT)
            .content(asJsonString(getTestBookDto()))
            .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    verify(bookService, times(1))
        .save(getTestBook());
  }

  @Test
  void  createShouldNameIsNotValid() throws Exception {

  }

  @Test
  void updateShouldUpdateSuccessful() throws Exception {
    mvc.perform(patch(ENDPOINT)
        .content(asJsonString(getTestBookDto()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(bookService, times(1))
        .update(getTestBook());
  }

  @Test
  void deleteShouldDeleteSuccessful() throws Exception {
    mvc.perform(delete(ENDPOINT)
        .content(asJsonString(getTestBookDto()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(bookService, times(1))
        .delete(getTestBook());
  }

  private Book getTestBook() {
    return Book.builder()
        .id(1)
        .name("Book")
        .author("Author")
        .holder(null)
        .yearOfWriting(1111)
        .build();
  }

  private BookDTO getTestBookDto() {
    return BookDTO.builder()
        .id(1)
        .name("Book")
        .author("Author")
        .holder(null)
        .yearOfWriting(1111)
        .build();
  }

  private String asJsonString(Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}