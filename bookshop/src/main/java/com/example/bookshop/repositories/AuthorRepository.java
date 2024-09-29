package com.example.bookshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Page<Author> findByAuthorNameContaining(String searchText, Pageable pageable);
}
