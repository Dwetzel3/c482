package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedProducts = FXCollections.observableArrayList();


    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static int lookupProduct(int productId) {
        boolean idFound = false;
        int index = 0;
        System.out.println("Looking for product ID: " + productId);
        for(int i = 0; i < getAllProducts().size(); i++){
            if(productId == (allProducts.get(i).getId())) {
                index = i;
                System.out.println("Product selected at index " + index);
                idFound  = true;
            }
        }
        if(idFound){
            return index;
        } else {
            System.out.println("Product not found.");
            return -1;
        }
    }

    public static int lookupPart(int partId){
        boolean idFound = false;
        int index = 0;
        System.out.println("Looking for part ID: " + partId);
            for(int i = 0; i < getAllParts().size(); i++){
                if(partId == (allParts.get(i).getId())) {
                    index = i;
                    System.out.println("Part selected at index " + index);
                    idFound  = true;
                }
            }
        if(idFound){
            return index;
        } else {
            Alert productNotFound = new Alert(Alert.AlertType.ERROR);
            productNotFound.setTitle("ERROR");
            productNotFound.setContentText("A product with this ID does not exist.");
            productNotFound.showAndWait();
            return -1;
        }
    }

    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }

    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    public static ObservableList<Part> getAllAssociated() {
        return associatedProducts;
    }
    public static void alertInvMax() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Your quantity must not exceed the maximum.");
        productNotFound.showAndWait();
    }
    public static void alertInvMin() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Your quantity must be greater than the minimum.");
        productNotFound.showAndWait();
    }
    public static void alertMinMax() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Your minimum must not exceed the maximum.");
        productNotFound.showAndWait();
    }
    public static void alertMaxMin() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Your maximum must be greater than the minimum.");
        productNotFound.showAndWait();
    }
    public static void alertPriceNeg() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Price must not be negative.");
        productNotFound.showAndWait();
    }
    public static void alertEmptyField() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Please complete all fields.");
        productNotFound.showAndWait();
    }
    public static void alertIsInt() {
        Alert productNotFound = new Alert(Alert.AlertType.ERROR);
        productNotFound.setTitle("ERROR");
        productNotFound.setContentText("Company field must be of string type.");
        productNotFound.showAndWait();
    }
    public static boolean isInt(String string) {
        if (string == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(string);
            alertIsInt();
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }
}