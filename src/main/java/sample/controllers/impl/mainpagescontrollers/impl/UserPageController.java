package sample.controllers.impl.mainpagescontrollers.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.model.CustomProduct;
import sample.model.User;
import sample.utils.UserSession;
import sample.controllers.impl.mainpagescontrollers.PageController;

import java.io.IOException;
import java.util.List;

public class UserPageController extends PageController {
    @FXML
    private Label name;
    @FXML
    public void initialize() {
        loadProducts();
        name.setText(UserSession.getInstance().getUsername());
    }
    @Override
    public String getResourceName() {
        return "/fxml/userPage.fxml";
    }

    @Override
    public List<CustomProduct> getProducts() {
        return super.productService.products("admin");
    }


}
