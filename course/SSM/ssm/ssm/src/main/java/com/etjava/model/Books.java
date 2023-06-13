package com.etjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    private Integer bookId;
    private String bookName;
    private Integer bookCounts;
    private String detail;
}
