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
        assertThat(store.price(Book.ONE)).isEqualTo(50.0);
    }

    @Test
    void two_same_books_no_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.ONE)).isEqualTo(100.0);
    }

    @Test
    void two_different_books_5_percent_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.TWO))
                .isEqualTo(100.0 * 0.95);
    }

    @Test
    void three_different_books_get_10_percent_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.TWO, Book.THREE)).isEqualTo(50 * 3 * 0.90);
    }
}
