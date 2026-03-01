package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;
import java.util.HashSet;
import java.util.Set;

public class BookStoreService {

    private static final double BASE_PRICE = 50.0;

    private static final double TWO_BOOK_DISCOUNT = 0.95;
    private static final double THREE_BOOK_DISCOUNT = 0.90;
    private static final double FOUR_BOOK_DISCOUNT = 0.80;
    private static final double FIVE_BOOK_DISCOUNT = 0.75;
    private static final double NO_DISCOUNT = 1.0;

    public double price(Book... books) {
        if (books.length == 0) {
            return 0.0;
        }

        Set<Book> uniqueBooks = new HashSet<>();
        for (Book book : books) {
            uniqueBooks.add(book);
        }

        double discountFactor = switch (uniqueBooks.size()) {
            case 2 -> TWO_BOOK_DISCOUNT;
            case 3 -> THREE_BOOK_DISCOUNT;
            case 4 -> FOUR_BOOK_DISCOUNT;
            case 5 -> FIVE_BOOK_DISCOUNT;
            default -> NO_DISCOUNT;
        };

        return books.length * BASE_PRICE * discountFactor;
    }
}
