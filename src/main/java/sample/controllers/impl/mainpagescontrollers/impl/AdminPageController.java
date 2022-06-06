package sample.controllers.impl.mainpagescontrollers.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sample.controllers.impl.mainpagescontrollers.PageController;
import sample.model.CustomProduct;
import sample.model.User;
import sample.services.user.UserService;
import sample.services.user.impl.UserServiceImpl;
import sample.utils.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminPageController extends PageController {
    private final UserService userService;
    @FXML
    private Label name;
    public AdminPageController() {
        this.userService = new UserServiceImpl();
    }
    @FXML
    public void initialize() {
        name.setText(UserSession.getInstance().getUsername());
        loadProducts();
    }

    @Override
    public String getResourceName() {
        return "/fxml/adminPage.fxml";
    }

    @Override
    public List<CustomProduct> getProducts() {
        return productService.products();
    }
    @FXML
    void processReportBtn(ActionEvent actionEvent) throws IOException{
        loadResource("/fxml/reportPage.fxml", actionEvent);
    }
    @FXML
    void processProductsBtn(ActionEvent actionEvent) throws IOException {
        loadResource("/fxml/adminProductsPage.fxml", actionEvent);
    }



}
