package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookStoreServiceTest {

    @Test
    void empty_basket_should_cost_zero() {
        var store = new BookStoreService();
        assertThat(store.price()).isZero();
    }

    @Test
    void one_book_costs_fifty_euros() {
        var store = new BookStoreService();
        assertThat(store.price(Book.I)).isEqualTo(50.0);
    }

    @Test
    void two_same_books_no_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.I, Book.I)).isEqualTo(100.0);
    }
}
