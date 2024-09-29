package com.example.bookshop.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class CartKey implements Serializable {
    private Integer bookId;
    private Integer customerId;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}
