package sample.dao.impl;
import sample.dao.dbconnection.DataBaseConnection;
import sample.dao.EntityDao;
import sample.model.Role;
import sample.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserDao extends EntityDao {
    public static final String INSERT_QUERY = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";
    public static final String SELECT_QUERY = "SELECT * FROM users WHERE username=? and password=?";
    public static final String SELECT_USERS_QUERY = "SELECT * FROM users";
    public static final String SELECT_USERS_PRODUCT_QUERY = "SELECT * FROM users inner join users_products"+
            " on users.id=users_products.user_id "+
            "inner join products on users_products.product_id=products.id where products.id=?";
    public static final String SELECT_USER_ROLE_QUERY = "SELECT roles.name FROM users inner join roles"+
            " on users.role_id=roles.id where users.id=?";
    public final RoleDao roleDao;

    public UserDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<User> users(){
        List<User> result = new LinkedList<>();
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_QUERY)) {
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while  (rs.next()){
                result.add(new User(rs.getInt("id"),
                        rs.getString("username")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
    public List<User> users(int productId){
        List<User> result = new LinkedList<>();
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_PRODUCT_QUERY)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while  (rs.next()){
                result.add(new User(rs.getInt("id"),
                        rs.getString("username")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public Optional<User> add(String username, String password, int roleId){
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, roleId);
            preparedStatement.executeUpdate();
            return getUserByUsernamePassword(username, password);
        } catch (SQLException e) {
            printSQLException(e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserByUsernamePassword(String username, String password){
        Connection connection = DataBaseConnection.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if (roleDao.getRoleNameById(rs.getInt("role_id")).isPresent()) {
                return Optional.of(new User(rs.getInt("id"),
                        rs.getString("username"),
                        new Role(roleDao.getRoleNameById(rs.getInt("role_id")).get())
                ));
            }
            else return Optional.empty();
        } catch (SQLException e) {
            printSQLException(e);
            return Optional.empty();
        }
    }
    public String getRoleName(int userId){
        String result = "";
        Connection connection = DataBaseConnection.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ROLE_QUERY)){
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            result=rs.getString("name");

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

}