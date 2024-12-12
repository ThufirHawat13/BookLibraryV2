package com.example.booklibraryv2.controllers;

import static org.mockito.ArgumentMatchers.any;
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
@Import(value = {BookController.class, CustomExceptionHandler.class})
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private BookService bookService;
  private final String ENDPOINT = "/books";

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
    when(bookService.findById(1L))
        .thenReturn(getTestBook());

    mvc.perform(get(ENDPOINT + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("Book"))
        .andExpect(jsonPath("$.author").value("Author"))
        .andExpect(jsonPath("$.holder").isEmpty())
        .andExpect(jsonPath("$.yearOfWriting")
            .value("1111"));

    verify(bookService, times(1))
        .findById(1L);
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
    when(bookService.save(getTestBook()))
        .thenReturn(getTestBook());

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(getTestBookDto()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("Book"))
        .andExpect(jsonPath("$.author").value("Author"))
        .andExpect(jsonPath("$.holder").isEmpty())
        .andExpect(jsonPath("$.yearOfWriting").value("1111"));

    verify(bookService, times(1))
        .save(getTestBook());
  }

  @Test
  void createShouldReturnBadRequestWhenNameIsEmpty() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setName("");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Name shouldn't be empty!"));

    verify(bookService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenNameBreaksMaximumLength() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setName("-------------------------------------------------------------------"
        + "--------------------------------------------------------------------------------------"
        + "-------------------------------------------------------------------------------------");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Length shouldn't be greater than 200!"));

    verify(bookService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenAuthorIsEmpty() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setAuthor("");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.author")
            .value("Author designation shouldn't be empty!"));

    verify(bookService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenAuthorBreaksMaximumLength() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setAuthor("-------------------------------------------------------------------"
        + "--------------------------------------------------------------------------------------"
        + "-------------------------------------------------------------------------------------");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.author")
            .value("Length shouldn't be greater than 200!"));

    verify(bookService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenYearOfWritingLowerThanMinimumAvailableValue()
      throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setYearOfWriting(-1);

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.yearOfWriting")
            .value("Year of writing shouldn't be lower than 0!"));

    verify(bookService, times(0))
        .save(any());
  }

  @Test
  void creteShouldReturnBadRequestWhenYearOfWritingGreaterThanMaximumAvailableValue()
      throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setYearOfWriting(3000);

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.yearOfWriting")
            .value("Year of writing shouldn't be greater than 2024!"));

    verify(bookService, times(0))
        .save(any());
  }

  @Test
  void updateShouldUpdateSuccessful() throws Exception {
    when(bookService.update(getTestBook()))
        .thenReturn(getTestBook());

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(getTestBookDto()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("Book"))
        .andExpect(jsonPath("$.author").value("Author"))
        .andExpect(jsonPath("$.holder").isEmpty())
        .andExpect(jsonPath("$.yearOfWriting").value("1111"));

    verify(bookService, times(1))
        .update(getTestBook());
  }

  @Test
  void updateShouldReturnBadRequestWhenNameIsEmpty() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setName("");

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Name shouldn't be empty!"));

    verify(bookService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenNameBreaksMaximumLength() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setName("-------------------------------------------------------------------"
        + "--------------------------------------------------------------------------------------"
        + "-------------------------------------------------------------------------------------");

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Length shouldn't be greater than 200!"));

    verify(bookService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenAuthorIsEmpty() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setAuthor("");

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.author")
            .value("Author designation shouldn't be empty!"));

    verify(bookService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenAuthorBreaksMaximumLength() throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setAuthor("-------------------------------------------------------------------"
        + "--------------------------------------------------------------------------------------"
        + "-------------------------------------------------------------------------------------");

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.author")
            .value("Length shouldn't be greater than 200!"));

    verify(bookService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenYearOfWritingLowerThanMinimumAvailableValue()
      throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setYearOfWriting(-1);

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.yearOfWriting")
            .value("Year of writing shouldn't be lower than 0!"));

    verify(bookService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenYearOfWritingGreaterThanMaximumAvailableValue()
      throws Exception {
    BookDTO notValidBookDTO = getTestBookDto();
    notValidBookDTO.setYearOfWriting(3000);

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(notValidBookDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.yearOfWriting")
            .value("Year of writing shouldn't be greater than 2024!"));

    verify(bookService, times(0))
        .update(any());
  }

  @Test
  void deleteShouldDeleteSuccessful() throws Exception {
    when(bookService.delete(1L))
        .thenReturn(getTestBook());

    mvc.perform(delete(ENDPOINT + "/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("Book"))
        .andExpect(jsonPath("$.author").value("Author"))
        .andExpect(jsonPath("$.holder").isEmpty())
        .andExpect(jsonPath("$.yearOfWriting").value("1111"));;

    verify(bookService, times(1))
        .delete(1L);
  }

  private Book getTestBook() {
    return Book.builder()
        .id(1L)
        .name("Book")
        .author("Author")
        .holder(null)
        .yearOfWriting(1111)
        .build();
  }

  private BookDTO getTestBookDto() {
    return BookDTO.builder()
        .id(1L)
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