package org.example.BookProject.managers;

import lombok.SneakyThrows;
import org.example.BookProject.models.User;
import org.example.BookProject.provider.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserManager {

    Logger logger = Logger.getLogger(UserManager.class.getName());
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    @SneakyThrows
    public User add(User user){
        String sql = "insert into User " +
                "(Name,Surname,email,password)" +
                "values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());

        int execute = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        int userId = generatedKeys.getInt(1);
        logger.info("New user created: " + user);
        user.setId(userId);
        return user;
    }


    @SneakyThrows
    public boolean existByEmail(String email) {
        List list = new ArrayList<>();
        Statement statement =connection.createStatement();
        String query = "Select email from user";
        ResultSet resultSet =statement.executeQuery(query);
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        boolean res = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(email)){
                res = true;
            }
        }
        return res;
    }

    @SneakyThrows
    public User getByEmailAndPassword(String email, String password) {
        User user = null;
        String query = "select * from user where email = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);

        ResultSet resultSet =preparedStatement.executeQuery();
        if (resultSet.next()){
            user = User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("Name"))
                    .surname(resultSet.getString("Surname"))
                    .build();
        }
        return user;
    }
}
