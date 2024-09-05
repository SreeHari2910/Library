package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query) {
        // Assuming the repository method exists for searching books by title or author
        return bookRepository.findByTitleContainingOrAuthorContainingOrIsbnContaining(query, query, query);
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
