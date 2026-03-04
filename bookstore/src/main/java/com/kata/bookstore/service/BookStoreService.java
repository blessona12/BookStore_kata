package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class BookStoreService {

    private static final BigDecimal BASE_PRICE = new BigDecimal("50.00");
    private static final BigDecimal NO_DISCOUNT = BigDecimal.ONE;

    private static final Map<Integer, BigDecimal> DISCOUNTS = Map.of(
            1, BigDecimal.ONE,
            2, new BigDecimal("0.95"),
            3, new BigDecimal("0.90"),
            4, new BigDecimal("0.80"),
            5, new BigDecimal("0.75")
    );

    private static final int MAX_GROUP_SIZE = 5;

    private final Map<String, BigDecimal> memo = new HashMap<>();

    public BigDecimal price(Book... books) {
        memo.clear();

        if (books == null) {
            throw new IllegalArgumentException("Books array cannot be null");
        }

        if (books.length == 0) {
            return BigDecimal.ZERO;
        }

        Map<Book, Integer> counts = new HashMap<>();
        for (Book book : books) {
            counts.merge(book, 1, Integer::sum);
        }

        return bestPrice(new BasketService(counts))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal bestPrice(BasketService basket) {
        String key = basket.key();

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (basket.totalBooks() == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal minPrice =
                BASE_PRICE.multiply(BigDecimal.valueOf(basket.totalBooks()));

        for (int size = MAX_GROUP_SIZE; size >= 1; size--) {

            if (basket.canFormGroup(size)) {

                BasketService remaining = basket.removeGroup(size);

                BigDecimal total =
                        calculateGroupPrice(size)
                                .add(bestPrice(remaining));

                minPrice = minPrice.min(total);
            }
        }

        memo.put(key, minPrice);
        return minPrice;
    }

    private BigDecimal calculateGroupPrice(int size) {

        BigDecimal discountFactor =
                DISCOUNTS.getOrDefault(size, NO_DISCOUNT);

        return BigDecimal.valueOf(size)
                .multiply(BASE_PRICE)
                .multiply(discountFactor);
    }
}