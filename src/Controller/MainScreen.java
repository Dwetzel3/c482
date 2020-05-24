package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;


public class MainScreen implements Initializable {

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partsIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partsQuantity;
    @FXML private TableColumn<Part, Double> priceColumn;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productID;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, Integer> productQuantity;
    @FXML private TableColumn<Product, Double> productPrice;
    @FXML private static Part selectedPart;
    @FXML private static Product selectedProduct;
    @FXML private TextField searchPartField;
    @FXML private TextField searchProductField;

    private void setPartToModify(Part selectedPart) {
        MainScreen.selectedPart = selectedPart;
    }

    static Product getProductToModify(){
        return selectedProduct;
    }

    private void setProductToModify(Product selectedProduct) {
        MainScreen.selectedProduct = selectedProduct;
    }

    public static Object getSelectedPart() {
        return selectedPart;
    }

    public static Object getSelectedProduct() {
        return selectedProduct;
    }

    @FXML void deletePart(ActionEvent event) {
        ObservableList<Part> allParts, entry;
        allParts = partsTable.getItems();
        entry = partsTable.getSelectionModel().getSelectedItems();
        allParts.removeAll(entry);
    }

    @FXML public void searchParts(ActionEvent event) {
            String partQuery = searchPartField.getText();
            partsTable.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(partQuery)));
    }

    @FXML public void searchProducts(ActionEvent event) {
        String productQuery = searchProductField.getText();
        productsTable.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(productQuery)));
    }

    @FXML void deleteProduct(ActionEvent event) {
        ObservableList<Product> allProducts, entry;
        allProducts = productsTable.getItems();
        entry = productsTable.getSelectionModel().getSelectedItems();
        allProducts.removeAll(entry);
    }

    public void exitApp(ActionEvent event) {
        System.exit(0);
    }

    //Goes to AddPart Scene
    @FXML public void sceneAddPart(ActionEvent event) throws IOException {
        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/AddPart.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Add Part");
        window.show();
    }


    //Goes to ModifyPart Scene
    @FXML public void sceneModifyPart(ActionEvent event) throws IOException {
        setPartToModify(partsTable.getSelectionModel().getSelectedItem());

        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/ModifyPart.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Modify Part");
        window.show();
    }


    //Goes to AddProduct Scene
    @FXML public void sceneAddProduct(ActionEvent event) throws IOException {
        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/AddProduct.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Add Product");
        window.show();
    }

    //Goes to ModifyProduct Scene
    @FXML public void sceneModifyProduct(ActionEvent event) throws IOException {
        setProductToModify(productsTable.getSelectionModel().getSelectedItem());

        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/ModifyProduct.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Modify Product");
        window.show();
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        //sort TableViews
        partsTable.getSortOrder().setAll();
        productsTable.getSortOrder().setAll();

        //set up initial values in table
        partsTable.setItems(getAllParts());

        //Setup PartTable
        partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Setup ProductTable
        productsTable.setItems(getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
