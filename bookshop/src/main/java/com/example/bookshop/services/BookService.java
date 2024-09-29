package com.example.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookshop.Dtos.BookDTO;
import com.example.bookshop.models.Author;
import com.example.bookshop.models.Book;
import com.example.bookshop.models.Topic;
import com.example.bookshop.repositories.BookRepository;

import org.springframework.web.multipart.MultipartFile;

@Service
public class BookService {
    private final BookRepository _bookRepository;
    private final FileService _fileService;

    @Autowired
    public BookService(BookRepository bookRepository, FileService fileService) {
        this._bookRepository = bookRepository;
        this._fileService = fileService;
    }

    public Iterable<Book> getAll() {
        Iterable<Book> books = this._bookRepository.findAll();
        for (Book book : books) {
            book.setBookPicture("/images/" + book.getBookPicture());
        }
        return books;
    }

    public Page<Book> paginationBooks(String searchText, Pageable pageable) {
        return this._bookRepository.findByBookNameContaining(searchText, pageable);
    }

    public Book saveBook(BookDTO bookDTO) {
        try {
            MultipartFile file = bookDTO.getBookPicture();
            String fileName = _fileService.saveFile(file);
            Author author = new Author();
            Topic topic = new Topic();
            author.setAuthorId(bookDTO.getAuthorId());
            topic.setTopicId(bookDTO.getTopicId());
            Book book = new Book();
            book.setBookName(bookDTO.getBookName());
            book.setBookPrice(bookDTO.getBookPrice());
            book.setBookQuantity(bookDTO.getBookQuantity());
            book.setBookDics(bookDTO.getBookDics());
            book.setBookPicture(fileName);
            book.setBookDiscount(bookDTO.getBookDiscount());
            book.setAuthor(author);
            book.setTopic(topic);

            return _bookRepository.save(book);
        } catch (Exception e) {
            return null;
        }
    }

    public Book updateBook(BookDTO bookDTO) {
        try {
            Book book = this._bookRepository.findById(bookDTO.getBookId()).get();
            String fileName = book.getBookPicture();
            if (!bookDTO.getBookPicture().isEmpty()) {
                MultipartFile file = bookDTO.getBookPicture();
                _fileService.removeFile(fileName);
                fileName = _fileService.saveFile(file);
            }
            Author author = new Author();
            Topic topic = new Topic();
            author.setAuthorId(bookDTO.getAuthorId());
            topic.setTopicId(bookDTO.getTopicId());
            book.setBookName(bookDTO.getBookName());
            book.setBookPrice(bookDTO.getBookPrice());
            book.setBookQuantity(bookDTO.getBookQuantity());
            book.setBookDics(bookDTO.getBookDics());
            book.setBookPicture(fileName);
            book.setBookDiscount(bookDTO.getBookDiscount());
            book.setAuthor(author);
            book.setTopic(topic);

            this._bookRepository.save(book);

            return book;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeBook(Integer bookId) {
        try {
            Book book = this._bookRepository.findById(bookId).get();
            _fileService.removeFile(book.getBookPicture());
            this._bookRepository.delete(book);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Book getBookById(Integer id) {
        Book book = this._bookRepository.findById(id).get();
        book.setBookPicture("/Images/" + book.getBookPicture());
        return book;
    }

    public Iterable<Book> searchBookByName(String bookName) {
        Iterable<Book> books = _bookRepository.searchBookByName(bookName);
        for (Book book : books) {
            book.setBookPicture("/images/" + book.getBookPicture());
        }
        return books;
    }

}
