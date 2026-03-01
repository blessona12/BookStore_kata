package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;
import java.util.HashMap;
import java.util.Map;

public class BookStoreService {

    private static final double BASE_PRICE = 50.0;
    private static final double NO_DISCOUNT = 1.0;

    private static final Map<Integer, Double> DISCOUNTS = Map.of(
            2, 0.95,
            3, 0.90,
            4, 0.80,
            5, 0.75
    );

    public double price(Book... books) {
        if (books.length == 0) {
            return 0.0;
        }

        Map<Book, Integer> counts = countBooks(books);
        int uniqueCount = counts.size();

        double discountFactor = DISCOUNTS.getOrDefault(uniqueCount, NO_DISCOUNT);

        int groupSize = uniqueCount;
        double groupPrice = groupSize * BASE_PRICE * discountFactor;

        int remainder = books.length - groupSize;
        double remainderPrice = remainder * BASE_PRICE;

        return groupPrice + remainderPrice;
    }

    private Map<Book, Integer> countBooks(Book[] books) {
        Map<Book, Integer> counts = new HashMap<>();
        for (Book book : books) {
            counts.put(book, counts.getOrDefault(book, 0) + 1);
        }
        return counts;
    }
}
