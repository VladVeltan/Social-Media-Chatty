package com.example.lab4final.repository.dbrepo;

import com.example.lab4final.domain.Friendship;
import com.example.lab4final.domain.Validator.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDbRepository  {
    private String url = "jdbc:postgresql://localhost:5432/chatty";
    private String username = "postgres";
    private String password = "Grivei123";
    private Validator<Friendship> validator;

    private  static FriendshipDbRepository instance = null;

    private FriendshipDbRepository(){}

    public static FriendshipDbRepository getInstance() {
        if (instance == null) {
            instance = new FriendshipDbRepository();
        }
        return instance;
    }



    public List<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = (int) resultSet.getLong("id");
                int id_User1 = (int) resultSet.getLong("id_user1");
                int id_User2 = (int) resultSet.getLong("id_user2");
                Timestamp dateTime = resultSet.getTimestamp("date");
                String status = resultSet.getString("status");
                Friendship friendship = new Friendship(id,id_User1,id_User2,dateTime,status);
                friendship.setId(id);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }


    public void save(Friendship entity) {
        String sql = "insert into friendships(id,id_user1,id_user2,date,status) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, entity.getId());
            ps.setInt(2, entity.getIdUser1());
            ps.setInt(3, entity.getIdUser2());
            ps.setTimestamp(4,entity.getDateTime());
            ps.setString(5, entity.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Friendship friendship) {
        String sql = "DELETE FROM friendships WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, friendship.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Friendship entity, Friendship newEntity) {
        String sql = "UPDATE friendships SET id_user1 = (?), id_user2 = (?), status = (?) WHERE id :: int=  (?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newEntity.getIdUser1());
            ps.setInt(2, newEntity.getIdUser2());
            ps.setString(3, newEntity.getStatus());
            ps.setInt(4,entity.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
