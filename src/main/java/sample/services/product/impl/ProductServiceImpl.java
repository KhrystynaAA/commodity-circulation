package sample.services.product.impl;

import sample.dao.impl.ProductDao;
import sample.model.CustomProduct;
import sample.model.User;
import sample.services.product.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl() {
        productDao=new ProductDao();
    }
    @Override
    public List<CustomProduct> products(int userId) {return productDao.products(userId);}

    @Override
    public List<CustomProduct> products(String rolename) {return productDao.products(rolename);}

    @Override
    public List<CustomProduct> products() {  return productDao.products();  }

    @Override
    public boolean add(int userId, int productId) {
        return (productDao.add(userId, productId));
    }

    @Override
    public void update(String roleName, int num) {productDao.update(roleName, num);}

    @Override
    public int number(int productId) {return productDao.number(productId);}



}
