package org.example.BookProject.managers;

import lombok.SneakyThrows;
import org.example.BookProject.models.Book;
import org.example.BookProject.models.User;
import org.example.BookProject.provider.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookManager {
    Logger logger = Logger.getLogger(UserManager.class.getName());
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();


    @SneakyThrows
    public void add(Book book) {
        String sql = "insert into  book " +
                "(name, year,page_count, user_id)" +
                " values (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,book.getName());
        preparedStatement.setInt(2,book.getYear());
        preparedStatement.setInt(3,book.getPageCount());
        preparedStatement.setInt(4,book.getUserid());

        int execute = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        int userId =generatedKeys.getInt(1);
        logger.info("New book added: " + book);
        book.setId(userId);
    }

    @SneakyThrows
    public void printAll() {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from book");
        ResultSet resultSet =statement.executeQuery();

        while (resultSet.next()){
            String name = resultSet.getString("name");
            int year = resultSet.getInt("year");
            int pageCount = resultSet.getInt("page_count");

            books.add(Book.builder().name(name).year(year).pageCount(pageCount).build());
        }
        for (Book book:books) {
            System.out.println("-----------------------");
            System.out.println(book);
        }
            System.out.println("-----------------------");
    }

    @SneakyThrows
    public void printUserBook(User author) {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from book where user_id = ?");
        statement.setInt(1,author.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            String name = resultSet.getString("name");
            int  year  = resultSet.getInt("year");
            int  pageCount  = resultSet.getInt("page_count");


            books.add(Book.builder()
                            .author(author)
                            .name(name)
                            .year(year)
                            .pageCount(pageCount)
                    .build());
        }

        for (Book book:books) {
            System.out.println(book);
        }
    }
}
