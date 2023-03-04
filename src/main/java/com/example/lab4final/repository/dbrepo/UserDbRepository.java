package com.example.lab4final.repository.dbrepo;

import com.example.lab4final.domain.User;
import com.example.lab4final.domain.Validator.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDbRepository {
    private String url = "jdbc:postgresql://localhost:5432/chatty";
    private String username = "postgres";
    private String password = "Grivei123";
    private Validator<User> validator;

    private static UserDbRepository instance = null;

    private UserDbRepository() {
    }

    public static UserDbRepository getInstance() {
        if (instance == null) {
            instance = new UserDbRepository();
        }
        return instance;

    }


    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = (int) resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String parola = resultSet.getString("parola");
                User utilizator = new User(id, firstName, lastName, email, parola);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void save(User entity) {
        String sql = "insert into users (id,first_name, last_name, email, parola) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId());
            ps.setString(2, entity.getPrenume());
            ps.setString(3, entity.getNume());
            ps.setString(4, entity.getEmail());
            ps.setString(5, entity.getParola());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(User e) {
    }
    public void update(User entity, User newEntity) {
    }
}
