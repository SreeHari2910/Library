package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Member;
import com.example.library.service.BookService;
import com.example.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LibraryWebController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index"; // This will render the index.html template
    }

    @GetMapping("/books")
    public String viewBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books"; // This will render the books.html template
    }

    @GetMapping("/members")
    public String viewMembers(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "members"; // This will render the members.html template
    }

    @PostMapping("/removeBook")
    public String removeBook(@RequestParam("id") Long id, Model model) {
        bookService.removeBook(id);
        return "redirect:/books"; // Redirect to the books page after removal
    }

    @PostMapping("/removeMember")
    public String removeMember(@RequestParam("id") Long id, Model model) {
        memberService.removeMember(id);
        return "redirect:/members"; // Redirect to the members page after removal
    }
}
