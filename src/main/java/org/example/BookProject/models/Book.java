package org.example.BookProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Book {
    private int id;
    private String name;
    private int year;
    private int pageCount;
    private int userid;
    private User author;

    @Override
    public String toString() {
        return
                         name +
                        " Էջերի քանակը: " + pageCount +
                        " Հեղինակ: " + author +
                        " Տարի: " + year;
    }
}
