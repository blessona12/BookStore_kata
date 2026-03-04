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

    @Test
    void two_same_and_one_different() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.ONE, Book.TWO)).isEqualTo(50 + 50 * 2 * 0.95);
    }

    @Test
    void two_pairs_of_different_books() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.ONE, Book.TWO, Book.TWO)).isEqualTo(2 * (50 * 2 * 0.95));
    }

    @Test
    void tricky_case_two_groups_of_four() {
        var store = new BookStoreService();
        assertThat(store.price(
                Book.ONE, Book.ONE,
                Book.TWO, Book.TWO,
                Book.THREE, Book.THREE,
                Book.FOUR, Book.FIVE
        )).isEqualTo(2 * (50 * 4 * 0.80)); // 51.2
    }
}
