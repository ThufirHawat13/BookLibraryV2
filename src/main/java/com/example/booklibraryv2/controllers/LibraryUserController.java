package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserResponseDTO;
import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserRequestDTO;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.mappers.LibraryUserMapper;
import com.example.booklibraryv2.services.LibraryUserService;
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
@RequiredArgsConstructor
@RequestMapping("/library-users")
public class LibraryUserController {

  private final LibraryUserService libraryUserService;

  @GetMapping
  public List<LibraryUserResponseDTO> getAll() {
    return libraryUserService.getAll().stream()
        .map(LibraryUserMapper::convertToLibraryUserDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public LibraryUserResponseDTO getById(@PathVariable Long id) {
    return LibraryUserMapper.convertToLibraryUserDTO(libraryUserService.findById(id));
  }

  @GetMapping("/find/{searchQuery}")
  public List<LibraryUserResponseDTO> findByNameContains(@PathVariable String searchQuery) {
    return libraryUserService.findByNameContains(searchQuery).stream()
        .map(LibraryUserMapper::convertToLibraryUserDTO)
        .collect(Collectors.toList());
  }

  @PostMapping()
  public ResponseEntity<LibraryUserResponseDTO> create(
      @RequestBody @Valid LibraryUserRequestDTO newLibraryUser) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(LibraryUserMapper
            .convertToLibraryUserDTO(
                libraryUserService.save(LibraryUserMapper
                    .convertToLibraryUser(newLibraryUser))));
  }

  @PatchMapping()
  public ResponseEntity<LibraryUserResponseDTO> update(
      @RequestBody @Valid LibraryUserRequestDTO updatedLibraryUser) throws ServiceException {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(LibraryUserMapper.convertToLibraryUserDTO(
            libraryUserService.update(LibraryUserMapper.convertToLibraryUser(updatedLibraryUser))));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
    libraryUserService.delete(id);

    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }


}
