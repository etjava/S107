package com.etjava.mapper;

import com.etjava.model.Books;

import java.util.List;
import java.util.Map;

public interface BookMapper {

    // ���һ����
    int addBook(Books books);
    // �޸�һ����
    int modifyBook(Books books);
    // ɾ��һ����
    int removeBook(Integer bookId);
    // ��ѯһ����
    Books findOne(Integer bookId);
    // ��ѯ������
    List<Books> find(Map<String,Object> map);
}
