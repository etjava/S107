package com.etjava.mapper;

import com.etjava.model.Books;

import java.util.List;
import java.util.Map;

public interface BookMapper {

    // 添加一本书
    int addBook(Books books);
    // 修改一本书
    int modifyBook(Books books);
    // 删除一本书
    int removeBook(Integer bookId);
    // 查询一本书
    Books findOne(Integer bookId);
    // 查询所有书
    List<Books> find(Map<String,Object> map);
}
