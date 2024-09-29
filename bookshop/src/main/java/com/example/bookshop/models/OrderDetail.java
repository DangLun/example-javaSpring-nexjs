package com.example.bookshop.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class OrderDetail {
    @EmbeddedId
    private OrderDetailKey id;

    private Integer odBuyQuantity;
    private Float odBuyPrice;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public OrderDetailKey getId() {
        return id;
    }

    public void setId(OrderDetailKey id) {
        this.id = id;
    }

    public Integer getOdBuyQuantity() {
        return odBuyQuantity;
    }

    public void setOdBuyQuantity(Integer odBuyQuantity) {
        this.odBuyQuantity = odBuyQuantity;
    }

    public Float getOdBuyPrice() {
        return odBuyPrice;
    }

    public void setOdBuyPrice(Float odBuyPrice) {
        this.odBuyPrice = odBuyPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
