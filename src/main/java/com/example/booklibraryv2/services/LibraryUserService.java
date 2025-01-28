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
  public LibraryUser save(LibraryUser newLibraryUser) {
    return libraryUserRepository.save(newLibraryUser);
  }

  @Transactional
  public LibraryUser update(LibraryUser updatedLibraryUser) throws ServiceException {
    if (libraryUserRepository.existsById(updatedLibraryUser.getId())) {
      return libraryUserRepository.save(updatedLibraryUser);
    } else {
      throw new ServiceException("Library user with id=%s isn't found!"
          .formatted(updatedLibraryUser.getId()));
    }
  }

  @Transactional
  public LibraryUser delete(Long id) {
    LibraryUser libraryUser = libraryUserRepository.findById(id)
            .orElseThrow(() -> new ServiceException("Library user with id=%s isn't found!"
                .formatted(id)));
    libraryUserRepository.deleteById(id);

    return libraryUser;
  }

}
