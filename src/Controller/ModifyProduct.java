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
import java.util.ResourceBundle;

import static Model.Inventory.*;

public class ModifyProduct implements Initializable {

    private Product selectedProduct;
    private Product product = new Product();

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partsIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partsQuantity;

    @FXML
    private TableColumn<Part, Double> priceColumn;

    @FXML private TextArea modProductId;
    @FXML private TextArea modProductName;
    @FXML private TextArea modProductInv;
    @FXML private TextArea modProductPrice;
    @FXML private TextArea modProductMax;
    @FXML private TextArea modProductMin;
    @FXML private TextField searchProductField;
    @FXML
    private TableView<Part> associatedTable;

    @FXML
    private TableColumn<Part, Integer> associatedId;

    @FXML
    private TableColumn<Part, String> associatedName;

    @FXML
    private TableColumn<Part, Integer> associatedInv;

    @FXML
    private TableColumn<Part, Double> associatedPrice;

    //Keeps list of unique IDs
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML public void saveButton(ActionEvent event) throws IOException {
        if (product.getAllAssociatedParts().size() > 0) {
            Boolean applicable = true;
            if (Integer.parseInt(modProductInv.getText()) > Integer.parseInt(modProductMax.getText())) {
                Inventory.alertInvMax();
                applicable = false;
            }
            if (Integer.parseInt(modProductInv.getText()) < Integer.parseInt(modProductMin.getText())) {
                Inventory.alertInvMin();
                applicable = false;
            }
            if (Integer.parseInt(modProductMin.getText()) > Integer.parseInt(modProductMax.getText())) {
                Inventory.alertMinMax();
                applicable = false;
            }
            if (Integer.parseInt(modProductMax.getText()) < Integer.parseInt(modProductMin.getText())) {
                Inventory.alertMaxMin();
                applicable = false;
            }
            if (Double.parseDouble(modProductPrice.getText()) < 0) {
                alertPriceNeg();
                applicable = false;
            }
            if (assocParts.size() == 0) {
                System.out.println("Product Needed.");
            }
            product.setId(Integer.parseInt(modProductId.getText()));
            product.setName(modProductName.getText());
            product.setStock(Integer.parseInt(modProductInv.getText()));
            product.setPrice(Double.parseDouble(modProductPrice.getText()));
            product.setMax(Integer.parseInt(modProductMax.getText()));
            product.setMin(Integer.parseInt(modProductMin.getText()));
            if (applicable == true && assocParts.size() > 0) {
                Inventory.updateProduct(Integer.parseInt(modProductId.getText()) -1, product);
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

    //Searches Products based on ID
    @FXML public void searchProducts(ActionEvent event) {
        String productQuery = searchProductField.getText();
        partsTable.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(productQuery)));
    }

    //Goes to main screen
    @FXML public void gotoMain(ActionEvent event) throws IOException {
        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Main Screen");
        window.show();
    }

    //Adds part from parts list to Associated List
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
            associatedTable.setItems(assocParts);
            System.out.println(product.getAllAssociatedParts());
        }
    }

    //Deletes part from associated list if button pressed
    @FXML void deleteAssociatedPart(ActionEvent event) {
        ObservableList<Part> allProducts, entry;
        assocParts = associatedTable.getItems();
        entry = associatedTable.getSelectionModel().getSelectedItems();
        assocParts.remove(entry);
        Part part = associatedTable.getSelectionModel().getSelectedItem();
        assocParts.remove(part);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Sorts TableViews
        //Sets up Parts Table
        partsTable.setItems(getAllParts());
        partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //selected product that is to be edited
        selectedProduct = (Product) MainScreen.getSelectedProduct();

        //Pulls in values for the selected Product
        modProductId.setText(Integer.toString(selectedProduct.getId()));
        modProductName.setText(selectedProduct.getName());
        modProductInv.setText(Integer.toString(selectedProduct.getStock()));
        modProductPrice.setText(Double.toString(selectedProduct.getPrice()));
        modProductMax.setText(Integer.toString(selectedProduct.getMax()));
        modProductMin.setText(Integer.toString(selectedProduct.getMin()));

        //Sets up associated Parts Table
        associatedTable.setItems(selectedProduct.getAllAssociatedParts());
        associatedId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
