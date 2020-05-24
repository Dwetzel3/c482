package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Model.Inventory.*;

public class AddProduct implements Initializable {

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partsIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partsQuantity;
    @FXML private TableColumn<Part, Double> priceColumn;
    @FXML private TextField searchPartField;
    @FXML private TextArea addName;
    @FXML private TextArea addStock;
    @FXML private TextArea addPrice;
    @FXML private TextArea addMax;
    @FXML private TextArea addMin;
    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, Integer> associatedID;
    @FXML private TableColumn<Part, String> associatedName;
    @FXML private TableColumn<Part, Integer> associatedQuantity;
    @FXML private TableColumn<Part, Double> associatedPrice;
    @FXML private int productId = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId()+1;


    private Product product = new Product();
    ArrayList<String> ArrayList = new ArrayList<>();

    private ObservableList<Part> assocParts = FXCollections.observableArrayList();


    @FXML public void saveButton(ActionEvent event) throws IOException {
        //ALERTS
        if (product.getAllAssociatedParts().size() > 0) {
            Boolean applicable = true;
            if (Integer.parseInt(addStock.getText()) > Integer.parseInt(addMax.getText())) {
                alertInvMax();
                applicable = false;
            }
            if (Integer.parseInt(addStock.getText()) < Integer.parseInt(addMin.getText())) {
                alertInvMin();
                applicable = false;
            }
            if (Integer.parseInt(addMin.getText()) > Integer.parseInt(addMax.getText())) {
                alertMinMax();
                applicable = false;
            }
            if (Integer.parseInt(addMax.getText()) < Integer.parseInt(addMin.getText())) {
                alertMaxMin();
                applicable = false;
            }
            if (Integer.parseInt(addPrice.getText()) < 0) {
                alertPriceNeg();
                applicable = false;
            }
            if (addName.getText().isEmpty() ||
                    addName.getText().isEmpty() ||
                    addStock.getText().isEmpty() ||
                    addMin.getText().isEmpty()    ||
                    addMax.getText().isEmpty()      ||
                    addPrice.getText().isEmpty()) {
                Inventory.alertEmptyField();
                applicable = false;
            }
            //Assigns values from TextFields
            product.setId(productId);
            product.setName(addName.getText());
            product.setStock(Integer.parseInt(addStock.getText()));
            product.setPrice(Double.parseDouble(addPrice.getText()));
            product.setMax(Integer.parseInt(addMax.getText()));
            product.setMin(Integer.parseInt(addMin.getText()));
            //If boolean is true, adds Product to List
            if (applicable) {
                Inventory.addProduct(product);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Main Screen");
        window.show();
    }

    //Searches table for product with entered ID
    @FXML public void searchProducts(ActionEvent event) {
        String productQuery = searchPartField.getText();
        partsTable.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(productQuery)));
    }


    //Add part from parts table to associated parts table
    @FXML public void addAssociated() {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        //checks if part is already assigned to associated list
        if (assocParts.contains(part)) {
            System.out.println("Duplicate");
        }
        else {
            product.addAssociatedPart(part);
            System.out.println(part.getId());
            assocParts.add(part);
            associatedPartsTable.setItems(assocParts);
        }
    }

    //Goes to Main Screen
    @FXML public void gotoMain(ActionEvent event) throws IOException {
        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Main Screen");
        window.show();
    }

    //Deletes part from Associated List & Table
    @FXML void delete(ActionEvent event) {
        ObservableList<Part> allProducts, entry;
        assocParts = associatedPartsTable.getItems();
        entry = associatedPartsTable.getSelectionModel().getSelectedItems();
        assocParts.remove(entry);
        Part part = associatedPartsTable.getSelectionModel().getSelectedItem();
        assocParts.remove(part);
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        partsTable.getSortOrder().setAll();
        partsTable.setItems(getAllParts());

        partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(product.getAllAssociatedParts());
        associatedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
