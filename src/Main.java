import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root, 995, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Sets up Main Screen Parts Table with initial parts
        Part part1 = new InHouse(1, "Part 1", 10.0, 50, 50, 50, 123123);
        Inventory.addPart(part1);
        Part part2 = new Outsourced(2, "Part 2", 520.0, 35, 0, 50, "Adios");
        Inventory.addPart(part2);
        Part part3 = new Outsourced(3, "Part 3", 120.0, 56, 10, 650, "GenComp");
        Inventory.addPart(part3);
        Part part4 = new InHouse(4, "Part 4", 80.0, 15, 5, 100, 10101);
        Inventory.addPart(part4);

        //Sets up Main screen Product table with initial products
        Product product1 = new Product(1, "Product 1", 50.0, 50, 1, 100);
        Inventory.addProduct(product1);
        Product product2 = new Product(2, "Product 2", 100.0, 120, 0, 200);
        Inventory.addProduct(product2);
        Product product3 = new Product(3, "Product 3", 200.0, 150, 5, 150);
        Inventory.addProduct(product3);
        Product product4 = new Product(4, "Product 4", 175.0, 10, 0, 25);
        Inventory.addProduct(product4);

        launch(args);
    }
}
