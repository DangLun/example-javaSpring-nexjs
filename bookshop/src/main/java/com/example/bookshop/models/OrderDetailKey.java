package com.example.bookshop.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailKey implements Serializable {
    private Integer orderId;
    private Integer bookId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
