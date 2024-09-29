package com.example.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookshop.models.Author;
import com.example.bookshop.repositories.AuthorRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Iterable<Author> getAll() {
        Iterable<Author> authors = this.authorRepository.findAll();
        return authors;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Integer authorId, Author authorDetails) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        author.setAuthorName(authorDetails.getAuthorName());
        author.setAuthorAddress(authorDetails.getAuthorAddress());
        author.setAuthorBio(authorDetails.getAuthorBio());
        author.setAuthorPhone(authorDetails.getAuthorPhone());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Integer authorId) {
        authorRepository.deleteById(authorId);
    }

    public Page<Author> searchAuthors(String searchText, Pageable pageable) {
        return authorRepository.findByAuthorNameContaining(searchText, pageable);
    }

    public Author getAuthorById(Integer authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

}
