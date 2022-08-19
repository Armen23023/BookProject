package org.example.BookProject.service;

import org.example.BookProject.managers.BookManager;
import org.example.BookProject.models.Book;
import org.example.BookProject.models.User;

public class BookService {


    BookManager bookManager = new BookManager();

    public void addBook(Book book){
        bookManager.add(book);
    };

    public void printAllbooks() {
          bookManager.printAll();
    }

    public void currentUserBook(User author) {
        bookManager.printUserBook(author);
    }
}
