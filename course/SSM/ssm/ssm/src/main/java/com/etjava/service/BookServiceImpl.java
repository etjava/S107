package com.etjava.service;

import com.etjava.mapper.BookMapper;
import com.etjava.model.Books;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//@Service
public class BookServiceImpl implements BookService{

    // set×¢Èë
    private BookMapper bookMapper;
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    @Override
    public int modifyBook(Books books) {
        return bookMapper.modifyBook(books);
    }

    @Override
    public int removeBook(Integer bookId) {
        return bookMapper.removeBook(bookId);
    }

    @Override
    public Books findOne(Integer bookId) {
        return bookMapper.findOne(bookId);
    }

    @Override
    public List<Books> find(Map<String, Object> map) {
        return bookMapper.find(map);
    }
}
