package com.example.bookshop.Dtos;

import org.springframework.web.multipart.MultipartFile;

import com.example.bookshop.models.Book;

public class BookDTO {
    private Integer bookId;
    private String bookName;
    private float bookPrice;
    private int bookQuantity;
    private String bookDics;
    private MultipartFile bookPicture;
    private String bookPictureURL;
    private float bookDiscount;
    private int authorId;
    private int topicId;

    public void mapBook(Book book) {
        this.setBookId(book.getBookId());
        this.setBookName(book.getBookName());
        this.setBookPrice(book.getBookPrice());
        this.setBookQuantity(book.getBookQuantity());
        this.setBookDics(book.getBookDics());
        this.setBookDiscount(book.getBookDiscount());
        this.setAuthorId(book.getAuthor().getAuthorId());
        this.setTopicId(book.getTopic().getTopicId());
        this.setBookPictureURL("/images/" + book.getBookPicture());
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookDics() {
        return bookDics;
    }

    public void setBookDics(String bookDics) {
        this.bookDics = bookDics;
    }

    public MultipartFile getBookPicture() {
        return bookPicture;
    }

    public void setBookPicture(MultipartFile bookPicture) {
        this.bookPicture = bookPicture;
    }

    public float getBookDiscount() {
        return bookDiscount;
    }

    public void setBookDiscount(float bookDiscount) {
        this.bookDiscount = bookDiscount;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getBookPictureURL() {
        return bookPictureURL;
    }

    public void setBookPictureURL(String bookPictureURL) {
        this.bookPictureURL = bookPictureURL;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

}
