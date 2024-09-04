package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    public Borrowing borrowBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (!book.isAvailable()) {
            throw new RuntimeException("Book not available for borrowing.");
        }

        List<Borrowing> borrowings = borrowingRepository.findByMemberId(memberId);
        if (borrowings.size() >= 3) {
            throw new RuntimeException("Member has already borrowed 3 books.");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        Borrowing borrowing = new Borrowing();
        borrowing.setBookId(bookId);
        borrowing.setMemberId(memberId);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusWeeks(2));
        borrowing.setReturned(false);
        return borrowingRepository.save(borrowing);
    }

    public void returnBook(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow();
        if (borrowing.isReturned()) {
            throw new RuntimeException("Book already returned.");
        }

        Book book = bookRepository.findById(borrowing.getBookId()).orElseThrow();
        book.setAvailable(true);
        bookRepository.save(book);

        borrowing.setReturned(true);
        borrowingRepository.save(borrowing);
    }
}
