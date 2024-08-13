package com.example.booklibraryv2.repositories;

import com.example.booklibraryv2.entities.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findByName(String name);
  List<Book> findByNameContains(String searchQuery);
}
