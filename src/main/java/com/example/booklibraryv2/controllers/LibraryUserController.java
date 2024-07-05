package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.dto.LibraryUserDTO;
import com.example.booklibraryv2.mappers.LibraryUserMapper;
import com.example.booklibraryv2.services.LibraryUserService;
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
  public List<LibraryUserDTO> getAll() {
    return libraryUserService.getAll().stream()
        .map(LibraryUserMapper::convertToLibraryUserDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public LibraryUserDTO getById(@PathVariable Integer id) {
    return LibraryUserMapper.convertToLibraryUserDTO(libraryUserService.findById(id));
  }

  @GetMapping("/find/{searchQuery}")
  public List<LibraryUserDTO> findByNameContains(@PathVariable String searchQuery) {
    return libraryUserService.findByNameContains(searchQuery).stream()
        .map(LibraryUserMapper::convertToLibraryUserDTO)
        .collect(Collectors.toList());
  }

  @PostMapping()
  public ResponseEntity<HttpStatus> create(@RequestBody LibraryUserDTO libraryUserDTO) {
    libraryUserService.save(LibraryUserMapper.convertToLibraryUser(libraryUserDTO));

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }

  @PatchMapping()
  public ResponseEntity<HttpStatus> update(@RequestBody LibraryUserDTO updatedLibraryUser) {
    libraryUserService.update(LibraryUserMapper.convertToLibraryUser(updatedLibraryUser));

    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

  @DeleteMapping()
  public ResponseEntity<HttpStatus> delete(@RequestBody LibraryUserDTO libraryUserForDelete) {
    libraryUserService.delete(LibraryUserMapper.convertToLibraryUser(libraryUserForDelete));

    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }


}
