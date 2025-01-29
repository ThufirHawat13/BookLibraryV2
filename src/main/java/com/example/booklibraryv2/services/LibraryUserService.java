package com.example.booklibraryv2.services;

import com.example.booklibraryv2.dto.libraryUserDTO.LibraryUserRequestDTO;
import com.example.booklibraryv2.entities.LibraryUser;
import com.example.booklibraryv2.exceptions.ServiceException;
import com.example.booklibraryv2.repositories.LibraryUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryUserService {

  private final LibraryUserRepository libraryUserRepository;

  public List<LibraryUser> getAll() {
    return libraryUserRepository.findAll();
  }

  public LibraryUser findById(Long id) {
    return findByIdOrThrow(id);
  }

  public List<LibraryUser> findByNameContains(String searchQuery) {
    return libraryUserRepository.findByNameContains(searchQuery);
  }

  public LibraryUser save(LibraryUser newLibraryUser) {
    var savedUser = libraryUserRepository.save(newLibraryUser);
    log.info("saved library user: {}", savedUser);

    return savedUser;
  }

  @Transactional
  public LibraryUser update(Long id, LibraryUserRequestDTO updatedFields) throws ServiceException {
    var updatedUser = updateFields(findByIdOrThrow(id), updatedFields);
    log.info("updated library user: {}", updatedUser);

    return updatedUser;
  }

  private LibraryUser updateFields(LibraryUser userForUpdate,
      LibraryUserRequestDTO updatedFields) {
    Optional.ofNullable(updatedFields.getName())
        .ifPresent(userForUpdate::setName);
    Optional.ofNullable(updatedFields.getSurname())
        .ifPresent(updatedFields::setSurname);

    return userForUpdate;
  }

  public LibraryUser delete(Long id) {
    LibraryUser libraryUser = libraryUserRepository.findById(id)
        .orElseThrow(() -> new ServiceException("Library user with id=%s isn't found!"
            .formatted(id)));
    libraryUserRepository.deleteById(id);

    return libraryUser;
  }

  private LibraryUser findByIdOrThrow(Long id) throws ServiceException {
    return libraryUserRepository.findById(id)
        .orElseThrow(() -> new ServiceException(
            "Library user with id=%s isn't found!"
                .formatted(id)));
  }
}
