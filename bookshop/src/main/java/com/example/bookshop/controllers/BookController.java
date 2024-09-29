package com.example.bookshop.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.Dtos.BookDTO;
import com.example.bookshop.models.Book;
import com.example.bookshop.services.BookService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService _bookService;

    public BookController(BookService bookService) {
        this._bookService = bookService;
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<Book> getAllBook() {
        return this._bookService.getAll();
    }

    @GetMapping(path = "/paginationBook")
    public ResponseEntity<Page<Book>> paginationBooks(
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "bookName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return ResponseEntity.ok(_bookService.paginationBooks(searchText, pageable));
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@ModelAttribute BookDTO bookDTO) {
        try {
            Book savedBook = _bookService.saveBook(bookDTO);
            return ResponseEntity.status(HttpStatus.OK).body(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi khi thêm sách: " + e.getMessage());
        }
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<?> searchBookByName(@RequestParam String bookName) {
        try {
            Iterable<Book> books = this._bookService.searchBookByName(bookName);
            return ResponseEntity.status(HttpStatus.CREATED).body(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/updateBook")
    public ResponseEntity<?> updateBook(@ModelAttribute BookDTO bookDTO) {
        return ResponseEntity.ok(this._bookService.updateBook(bookDTO));
    }

    @DeleteMapping("/deleteBook")
    public String deleteBook(@RequestParam Integer bookId) {
        return this._bookService.removeBook(bookId) ? "Đã xóa thành công sách" : "Đã xóa thất bại";
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(this._bookService.getBookById(id));
    }

}
