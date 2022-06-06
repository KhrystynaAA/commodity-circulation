package sample.services.product;

import sample.model.CustomProduct;

import java.util.List;

public interface ProductService {
    List<CustomProduct> products(int userId);
    List<CustomProduct> products(String rolename);
    List<CustomProduct> products();
    boolean add(int userId, int productId);
    void update(String roleName, int num);
    int number(int productId);
}
