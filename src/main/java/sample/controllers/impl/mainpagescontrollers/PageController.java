package sample.controllers.impl.mainpagescontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.model.CustomProduct;
import sample.services.product.ProductService;
import sample.services.product.impl.ProductServiceImpl;
import sample.services.user.UserService;
import sample.services.user.impl.UserServiceImpl;
import sample.utils.UserSession;
import sample.controllers.PageLoader;

import java.io.IOException;
import java.util.List;

public abstract class PageController implements PageLoader {
    public final ProductService productService;
    public final UserService userService;
    @FXML
    private ListView<ButtonBar> products;

    protected PageController() {
        this.userService = new UserServiceImpl();
        this.productService = new ProductServiceImpl();
    }

    public abstract String getResourceName();

    public abstract List<CustomProduct> getProducts();

    public void loadProducts() {
        if (products == null) {
            products = new ListView<>();
        }
        for (CustomProduct product : getProducts()) {

            Label tmpLbl1 = new Label(product.getName());
            Label tmpLbl2 = new Label(String.valueOf(product.getPrice()));
            ButtonBar bar = new ButtonBar();
            bar.getButtons().addAll(tmpLbl1,tmpLbl2, getAddButton(product));
            products.getItems().add(bar);

        }

    }

    private Button getAddButton(CustomProduct product){
        Button ba = new Button();
        ImageView im = new ImageView("/img/add.png");
        ba.setStyle("-fx-background-color: white");
        im.setFitWidth(20);
        im.setFitHeight(20);
        ba.setMaxWidth(5);
        ba.setGraphic(im);
        ba.setOnAction(event -> {
            productService.add(UserSession.getInstance().getId(), product.getId());
            if (userService.getRoleName(UserSession.getInstance().getId()).equals("ADMIN")) productService.update(userService.getRoleName(UserSession.getInstance().getId()),100);

            try {
                refreshStage(event);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        return ba;
    }

    protected void refreshStage(ActionEvent actionEvent) throws IOException {
        loadResource(getResourceName(), actionEvent);
    }

    @FXML
    void processLogOutBtn(ActionEvent actionEvent) throws IOException {
        UserSession.getInstance().cleanUserSession();
        loadResource("/fxml/index.fxml", actionEvent);
    }

}
