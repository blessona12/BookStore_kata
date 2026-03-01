package com.kata.bookstore.service;

import com.kata.bookstore.model.Book;

public class BookStoreService {

    public double price(Book... books) {
        return books.length * 50.0;
    }
}
