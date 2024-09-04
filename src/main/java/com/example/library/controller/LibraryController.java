package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.model.Member;
import com.example.library.service.BookService;
import com.example.library.service.BorrowingService;
import com.example.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class LibraryController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book savedBook = bookService.addBook(book);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/books/{id}")
    public void removeBook(@PathVariable Long id) {
        bookService.removeBook(id);
    }

    @GetMapping("/books/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author,
                                  @RequestParam(required = false) String isbn) {
        return bookService.searchBooks(title, author, isbn);
    }

    @GetMapping("/books")
    @ResponseBody
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/members")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        try {
            Member savedMember = memberService.addMember(member);
            return ResponseEntity.ok(savedMember);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/members/{id}")
    public void removeMember(@PathVariable Long id) {
        memberService.removeMember(id);
    }

    @GetMapping("/members")
    @ResponseBody
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping("/borrowings/borrow")
    public Borrowing borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return borrowingService.borrowBook(bookId, memberId);
    }

    @PostMapping("/borrowings/return/{id}")
    public void returnBook(@PathVariable Long id) {
        borrowingService.returnBook(id);
    }
}
