package sample.controllers.impl.mainpagescontrollers.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.controllers.impl.mainpagescontrollers.PageController;
import sample.model.CustomProduct;
import sample.services.user.UserService;
import sample.services.user.impl.UserServiceImpl;
import sample.utils.UserSession;

import java.io.IOException;
import java.util.List;

public class AdminProductsPageController extends PageController {
    @Override
    public String getResourceName() {return "/fxml/adminProductsPage.fxml";    }
    @FXML
    private Label name;
    @Override
    public List<CustomProduct> getProducts() {
        return super.productService.products(UserSession.getInstance().getId());
    }
    @FXML
    void processBackBtn(ActionEvent actionEvent) throws IOException {
        loadResource("/fxml/adminPage.fxml", actionEvent);
    }
    @FXML
    private ListView<ButtonBar> products;
    public void initialize() {
        name.setText(UserSession.getInstance().getUsername());
        listProducts();
    }
    public void listProducts() {
        if (products == null) {
            products = new ListView<>();
        }
        for (CustomProduct product : getProducts()) {
            Label tmpLbl1 = new Label(product.getName());
            Label tmpLbl2 = new Label(String.valueOf(product.getPrice()));
            Label tmpLbl3 = new Label(String.valueOf(productService.number(product.getId())));
            ButtonBar bar = new ButtonBar();
            bar.getButtons().addAll(tmpLbl1,tmpLbl2, tmpLbl3);
            products.getItems().add(bar);

        }

    }

}
