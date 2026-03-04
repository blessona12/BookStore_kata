package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;
import java.util.HashMap;
import java.util.Map;

public class BookStoreService {

    private static final double BASE_PRICE = 50.0;
    private static final double NO_DISCOUNT = 1.0;

    private static final Map<Integer, Double> DISCOUNTS = Map.of(
            1, 1.0,
            2, 0.95,
            3, 0.90,
            4, 0.80,
            5, 0.75
    );

    private static final int MAX_GROUP_SIZE = 5;

    private final Map<String, Double> memo = new HashMap<>();

    public double price(Book... books) {
        if (books.length == 0) {
            return 0.0;
        }

        Map<Book, Integer> counts = new HashMap<>();
        for (Book book : books) {
            counts.merge(book, 1, Integer::sum);
        }

        return bestPrice(new BasketService(counts));
    }

    private double bestPrice(BasketService basket) {
        String key = basket.key();

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (basket.totalBooks() == 0) {
            return 0.0;
        }

        double minPrice = basket.totalBooks() * BASE_PRICE;

        for (int size = MAX_GROUP_SIZE; size >= 1; size--) {
            if (basket.canFormGroup(size)) {

                BasketService remaining = basket.removeGroup(size);

                double total =
                        calculateGroupPrice(size) +
                                bestPrice(remaining);

                minPrice = Math.min(minPrice, total);
            }
        }

        memo.put(key, minPrice);
        return minPrice;
    }

    private double calculateGroupPrice(int groupSize) {
        double discountFactor =
                DISCOUNTS.getOrDefault(groupSize, NO_DISCOUNT);

        return groupSize * BASE_PRICE * discountFactor;
    }
}