package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserRequest;
import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserResponse;
import com.example.booklibraryv2.dto.validationGroups.CreateGroup;
import com.example.booklibraryv2.dto.validationGroups.UpdateGroup;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.mappers.LibraryUserMapper;
import com.example.booklibraryv2.services.LibraryUserService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/library-users")
public class LibraryUserController {

  private final LibraryUserService libraryUserService;
  private final LibraryUserMapper libraryUserMapper;


  @GetMapping
  public List<LibraryUserResponse> getAll() {
    return libraryUserMapper.toResponses(
        libraryUserService.getAll());
  }

  @GetMapping("/{id}")
  public LibraryUserResponse getById(
      @PathVariable Long id) {
    return libraryUserMapper.toResponse(
        libraryUserService.findById(id));
  }

  @GetMapping("/find/{searchQuery}")
  public List<LibraryUserResponse> findByNameContains(
      @PathVariable String searchQuery) {
    return libraryUserMapper.toResponses(
        libraryUserService.findByNameContains(searchQuery));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LibraryUserResponse create(
      @RequestBody @Validated(CreateGroup.class) LibraryUserRequest newLibraryUser) {
    return libraryUserMapper.toResponse(
        libraryUserService.save(
            libraryUserMapper.toEntity(newLibraryUser)));
  }

  @PatchMapping("/{id}")
  public LibraryUserResponse update(
      @PathVariable(name = "id") Long id,
      @RequestBody @Validated(UpdateGroup.class) LibraryUserRequest updatedLibraryUser)
      throws ServiceException {
    return libraryUserMapper.toResponse(
        libraryUserService.update(id, updatedLibraryUser));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable(name = "id") Long id) {
    libraryUserService.delete(id);
  }


}
