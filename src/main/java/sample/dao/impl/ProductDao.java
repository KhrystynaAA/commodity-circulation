package sample.dao.impl;

import sample.dao.EntityDao;
import sample.dao.dbconnection.DataBaseConnection;
import sample.model.CustomProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductDao extends EntityDao {

    public static final String INSERT_PRODUCT_USER_QUERY = "INSERT INTO users_products (user_id, product_id) VALUES (?, ?)";
     public static final String SELECT_QUERY = "SELECT * FROM products";
    public static final String SELECT_PRODUCTS_QUERY = "SELECT * FROM products inner join users_products" +
            " on products.id=users_products.product_id where users_products.user_id=?";
    public static final String SELECT_PRODUCTS_USER_QUERY = "SELECT * FROM products inner join users_products"+
            " on products.id=users_products.product_id "+
            "inner join users on users_products.user_id=users.id"+
            " inner join roles on users.role_id=roles.id where roles.name=?";
    public static final String SELECT_PRODUCT_NUMBER_QUERY = "SELECT users_products.number FROM users_products inner join products" +
            " on users_products.product_id=products.id where users_products.product_id=?";
    public static final String DELETE_PRODUCT_QUERY = "DELETE FROM products where id=?";
    public static final String DELETE_PRODUCT_USER_QUERY = "DELETE FROM users_products where user_id=? and product_id=?";
    public static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name=?, price=? WHERE id=?";
    public static final String UPDATE_PRODUCT_USER_QUERY = "UPDATE users_products inner join users on users.id=users_products.user_id"+
            " inner join roles on roles.id=users.role_id SET number=? WHERE roles.name=?";


    public List<CustomProduct> products(int userId) {
        List<CustomProduct> result = new LinkedList<>();
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new CustomProduct(rs.getInt("id"),
                        rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
    public List<CustomProduct> products(String rolename) {
        List<CustomProduct> result = new LinkedList<>();

        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_USER_QUERY)) {
            preparedStatement.setString(1, rolename);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                double price = rs.getDouble("price");
                if(rolename=="admin") price+=0.15*price;
                result.add(new CustomProduct(rs.getInt("id"),
                        rs.getString("name"), price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
    public List<CustomProduct> products() {
        List<CustomProduct> result = new LinkedList<>();
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new CustomProduct(rs.getInt("id"),
                        rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
    public int number(int productId){
        int num=0;
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement preparedStatementProductNumber = connection.prepareStatement(SELECT_PRODUCT_NUMBER_QUERY)) {
            preparedStatementProductNumber.setInt(1, productId);
            preparedStatementProductNumber.executeQuery();
            ResultSet rs = preparedStatementProductNumber.executeQuery();
            rs.next();
            num= rs.getInt("number");

        } catch (SQLException e) {
            printSQLException(e);

        }
        return num;
    }
    public boolean add(int userId, int productId) {
        Connection connection = DataBaseConnection.getConnection();
        int productIdCheck=0;
        for (CustomProduct product:products(userId)) {
            if(product.getId()==productId){
                productIdCheck=productId;
            }
        }
        if (productId == productIdCheck && productId!=0) return false;
        else {
            try (PreparedStatement preparedStatementProductUser = connection.prepareStatement(INSERT_PRODUCT_USER_QUERY)) {

                preparedStatementProductUser.setInt(1, userId);
                preparedStatementProductUser.setInt(2, productId);
                preparedStatementProductUser.executeUpdate();

                return true;
            } catch (SQLException e) {
                printSQLException(e);
                return false;
            }
        }
    }
    public void update(String roleName, int num) {
        Connection connection = DataBaseConnection.getConnection();

            try (PreparedStatement preparedStatementProductUser = connection.prepareStatement(UPDATE_PRODUCT_USER_QUERY)) {

                preparedStatementProductUser.setString(2, roleName);
                preparedStatementProductUser.setInt(1, num);
                preparedStatementProductUser.executeUpdate();

            } catch (SQLException e) {
                printSQLException(e);

            }

    }



}
