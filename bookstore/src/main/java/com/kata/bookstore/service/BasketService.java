package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class BasketService {
    private final int[] counts;

    public BasketService(Map<Book, Integer> countMap) {
        counts = new int[Book.values().length];
        for (Map.Entry<Book, Integer> entry : countMap.entrySet()) {
            counts[entry.getKey().ordinal()] = entry.getValue();
        }
    }

    public BasketService(int[] counts) {
        this.counts = counts.clone();
    }

    public int totalBooks() {
        return Arrays.stream(counts).sum();
    }

    public boolean canFormGroup(int size) {
        long unique = Arrays.stream(counts).filter(c -> c > 0).count();
        return unique >= size;
    }

    public BasketService removeGroup(int size) {
        int[] newCounts = counts.clone();
        int removed = 0;
        for (int i = 0; i < newCounts.length && removed < size; i++) {
            if (newCounts[i] > 0) {
                newCounts[i]--;
                removed++;
            }
        }
        return new BasketService(newCounts);
    }

    // For memo key
    public String key() {
        return Arrays.toString(counts);
    }
}