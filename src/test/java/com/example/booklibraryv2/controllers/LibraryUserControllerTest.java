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

import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.services.LibraryUserService;
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
@Import(value = {LibraryUserController.class, CustomExceptionHandler.class})
@ExtendWith(MockitoExtension.class)
class LibraryUserControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private LibraryUserService libraryUserService;
  private final String ENDPOINT = "/library-users";

  @Test
  void getAllShouldReturnLibraryUserDtoList() throws Exception {
    when(libraryUserService.getAll())
        .thenReturn(List.of(getTestLibraryUser()));

    mvc.perform(get(ENDPOINT)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Name"))
        .andExpect(jsonPath("$[0].surname").value("Surname"))
        .andExpect(jsonPath("$[0].bookList").isEmpty());

    verify(libraryUserService, times(1))
        .getAll();
  }

  @Test
  void getByIdShouldReturnLibraryUserDto() throws Exception {
    when(libraryUserService.findById(1L))
        .thenReturn(getTestLibraryUser());

    mvc.perform(get(ENDPOINT + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Name"))
        .andExpect(jsonPath("$.surname").value("Surname"))
        .andExpect(jsonPath("$.bookList").isEmpty());

    verify(libraryUserService, times(1))
        .findById(1L);
  }

  @Test
  void findByNameContainsShouldReturnLibraryUserDtoList() throws Exception {
    when(libraryUserService.findByNameContains("Name"))
        .thenReturn(List.of(getTestLibraryUser()));

    mvc.perform(get(ENDPOINT + "/find/Name")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Name"))
        .andExpect(jsonPath("$[0].surname").value("Surname"))
        .andExpect(jsonPath("$[0].bookList").isEmpty());

    verify(libraryUserService, times(1))
        .findByNameContains("Name");
  }

  @Test
  void createShouldCreateSuccessful() throws Exception {
    when(libraryUserService.save(getTestLibraryUser()))
        .thenReturn(getTestLibraryUser());

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(getTestLibraryUserDto()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Name"))
        .andExpect(jsonPath("$.surname").value("Surname"))
        .andExpect(jsonPath("$.bookList").isEmpty());

    verify(libraryUserService, times(1))
        .save(getTestLibraryUser());
  }

  @Test
  void createShouldReturnBadRequestWhenNameIsEmpty() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setName("");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidLibraryUserDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Name shouldn't be empty!"));

    verify(libraryUserService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenNameBreaksMaximumLength() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setName("SultanSuleimanBertaMariaBenderBeiSultan"
        + "SuleimanBertaMariaBenderBeiSultanSuleimanBertaMariaBenderBei");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidLibraryUserDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Length shouldn't be greater than 30!"));

    verify(libraryUserService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenSurnameIsEmpty() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setSurname("");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidLibraryUserDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.surname")
            .value("Surname shouldn't be empty!"));

    verify(libraryUserService, times(0))
        .save(any());
  }

  @Test
  void createShouldReturnBadRequestWhenSurnameBreaksMaximumLength() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setSurname("SultanSuleimanBertaMariaBenderBeiSultan"
        + "SuleimanBertaMariaBenderBeiSultanSuleimanBertaMariaBenderBei");

    mvc.perform(post(ENDPOINT)
            .content(asJsonString(notValidLibraryUserDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.surname")
            .value("Length shouldn't be greater than 30!"));

    verify(libraryUserService, times(0))
        .save(any());
  }

  @Test
  void updateShouldUpdateSuccessful() throws Exception {
    when(libraryUserService.update(getTestLibraryUser()))
        .thenReturn(getTestLibraryUser());

    mvc.perform(patch(ENDPOINT)
            .content(asJsonString(getTestLibraryUserDto()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Name"))
        .andExpect(jsonPath("$.surname").value("Surname"))
        .andExpect(jsonPath("$.bookList").isEmpty());

    verify(libraryUserService, times(1))
        .update(getTestLibraryUser());
  }

  @Test
  void updateShouldReturnBadRequestWhenNameIsEmpty() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setName("");

    mvc.perform(patch(ENDPOINT)
        .content(asJsonString(notValidLibraryUserDTO))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Name shouldn't be empty!"));

    verify(libraryUserService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenNameBreaksMaximumLength() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setName("-------------------------------");

    mvc.perform(patch(ENDPOINT)
        .content(asJsonString(notValidLibraryUserDTO))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name")
            .value("Length shouldn't be greater than 30!"));

    verify(libraryUserService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenSurnameIsEmpty() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setSurname("");

    mvc.perform(patch(ENDPOINT)
        .content(asJsonString(notValidLibraryUserDTO))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.surname")
            .value("Surname shouldn't be empty!"));

    verify(libraryUserService, times(0))
        .update(any());
  }

  @Test
  void updateShouldReturnBadRequestWhenSurnameBreaksMaximumLength() throws Exception {
    LibraryUserDTO notValidLibraryUserDTO = getTestLibraryUserDto();
    notValidLibraryUserDTO.setSurname("-------------------------------");

    mvc.perform(patch(ENDPOINT)
        .content(asJsonString(notValidLibraryUserDTO))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.surname")
            .value("Length shouldn't be greater than 30!"));

    verify(libraryUserService, times(0))
        .update(any());
  }

  @Test
  void deleteShouldDeleteSuccessful() throws Exception {
    when(libraryUserService.delete(1L))
        .thenReturn(getTestLibraryUser());

    mvc.perform(delete(ENDPOINT + "/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Name"))
        .andExpect(jsonPath("$.surname").value("Surname"))
        .andExpect(jsonPath("$.bookList").isEmpty());

    verify(libraryUserService, times(1))
        .delete(1L);
  }

  private LibraryUser getTestLibraryUser() {
    return LibraryUser.builder()
        .id(1L)
        .name("Name")
        .surname("Surname")
        .bookList(null)
        .build();
  }

  private LibraryUserDTO getTestLibraryUserDto() {
    return LibraryUserDTO.builder()
        .id(1L)
        .name("Name")
        .surname("Surname")
        .bookList(null)
        .build();
  }

  private String asJsonString(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}