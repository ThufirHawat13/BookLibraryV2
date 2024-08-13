package com.example.booklibraryv2.services;

import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.repositories.LibraryUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryUserService {

  private final LibraryUserRepository libraryUserRepository;

  public List<LibraryUser> getAll() {
    return libraryUserRepository.findAll();
  }

  public LibraryUser findById(Long id) {
    return libraryUserRepository.findById(id)
        .orElseThrow(() -> new ServiceException("Library user with id = %d isn't found!"
            .formatted(id)));
  }

  public List<LibraryUser> findByNameContains(String searchQuery) {
    return libraryUserRepository.findByNameContains(searchQuery);
  }

  @Transactional
  public void save(LibraryUser libraryUser) {
    libraryUserRepository.save(libraryUser);
  }

  @Transactional
  public void update(LibraryUser updatedLibraryUser) {
    LibraryUser libraryUser = libraryUserRepository.findById(updatedLibraryUser.getId())
        .orElse(null);

    libraryUser.setName(libraryUser.getName());
    libraryUser.setSurname(libraryUser.getSurname());
    libraryUser.setBookList(libraryUser.getBookList());
  }

  @Transactional
  public void delete(Long id) {
    libraryUserRepository.deleteById(id);
  }

}
