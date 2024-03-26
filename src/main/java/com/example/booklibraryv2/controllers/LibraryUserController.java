package com.example.booklibraryv2.controllers;

import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.services.LibraryUserService;
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
@RequiredArgsConstructor
@RequestMapping("/libraryUsers")
public class LibraryUserController {

  @Autowired
  private final LibraryUserService libraryUserService;

  @GetMapping
  public List<LibraryUser> index() {
    return libraryUserService.getAll();
  }

  @GetMapping("/find/{searchQuery}")
  public List<LibraryUser> findByNameContains(@PathVariable String searchQuery) {
    return libraryUserService.findByNameContains(searchQuery);
  }

  @PostMapping("/add")
  public ResponseEntity<HttpStatus> addNew(@RequestBody LibraryUser libraryUser) {
    libraryUserService.save(libraryUser);

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @PatchMapping("/update")
  public ResponseEntity<HttpStatus> update(@RequestBody LibraryUser updatedLibraryUser) {
    libraryUserService.update(updatedLibraryUser);

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @DeleteMapping("/remove")
  public ResponseEntity<HttpStatus> remove(@RequestBody LibraryUser libraryUserForDelete) {
    libraryUserService.delete(libraryUserForDelete);

    return ResponseEntity.ok(HttpStatus.OK);
  }


}
