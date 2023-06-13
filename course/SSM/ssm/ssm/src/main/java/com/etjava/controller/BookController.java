package com.etjava.controller;

import com.etjava.model.Books;
import com.etjava.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
//    @Qualifier("BookServiceImpl") // 直接在IOC容器中获取指定的bean 也可以自动扫描
    private BookService bookService;


    // 查询全部书籍信息
    @RequestMapping("/allBook")
    public String list(Model model) {
        List<Books> list = bookService.find(null);
        System.out.println(list.size());
        model.addAttribute("bookList", list);
        return "allbook";
    }

    @RequestMapping("/preAddBook")
    public String toAddPaper() {
        return "addBook";
    }
    @RequestMapping("/addBook")
    public String addPaper(Books books) {
        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(Model model, int id) {
        Books books = bookService.findOne(id);
        System.out.println(books);
        model.addAttribute("book",books );
        return "updateBook";
    }
    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book) {
        System.out.println(book);
        bookService.modifyBook(book);
        Books books = bookService.findOne(book.getBookId());
        model.addAttribute("books", books);
        return "redirect:/book/allBook";
    }
    @RequestMapping("/del/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        bookService.removeBook(id);
        return "redirect:/book/allBook";
    }
}