package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        assertThat(store.price(Book.ONE)).isEqualByComparingTo("50.0");
    }

    @Test
    void two_same_books_no_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.ONE)).isEqualByComparingTo("100.0");
    }

    @Test
    void two_different_books_5_percent_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.TWO))
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("95.0"));
    }

    @Test
    void three_different_books_get_10_percent_discount() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.TWO, Book.THREE)).usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("135.0"));
    }

    @Test
    void two_same_and_one_different() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.ONE, Book.TWO)).usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("145.0"));
    }

    @Test
    void two_pairs_of_different_books() {
        var store = new BookStoreService();
        assertThat(store.price(Book.ONE, Book.ONE, Book.TWO, Book.TWO)).usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("190.0"));
    }

    @Test
    void tricky_case_two_groups_of_four() {
        var store = new BookStoreService();
        assertThat(store.price(
                Book.ONE, Book.ONE,
                Book.TWO, Book.TWO,
                Book.THREE, Book.THREE,
                Book.FOUR, Book.FIVE
        )).usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("320.0"));
    }
}
