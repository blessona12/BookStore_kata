package com.kata.bookstore.service;

public class BookStoreService {

    public double price(int... bookIds) {
        return bookIds.length * 50.0;
    }
}
