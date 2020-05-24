package Controller;

import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.InHouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {
    @FXML private RadioButton radioInHouse;
    @FXML private ToggleGroup partGroup;
    @FXML private RadioButton radioOutsourced;
    @FXML private TextArea modPartId;
    @FXML private TextArea modPartName;
    @FXML private TextArea modInvCount;
    @FXML private TextArea companyName;
    @FXML private Label companyNameLabel;
    @FXML private TextArea modPrice;
    @FXML private TextArea modMax;
    @FXML private TextArea modMin;
    private Part selectedPart;

    @FXML public void saveButton(ActionEvent event) throws IOException {
        //Alerts
        Boolean applicable = true;
        if (companyName.getText().isEmpty() ||
                modPartName.getText().isEmpty() ||
                modInvCount.getText().isEmpty() ||
                modPrice.getText().isEmpty()    ||
                modMax.getText().isEmpty()      ||
                modMin.getText().isEmpty()) {
            Inventory.alertEmptyField();
            applicable = false;
        }
        if (Integer.parseInt(modInvCount.getText()) > Integer.parseInt(modMax.getText())) {
            Inventory.alertInvMax();
            applicable = false;
        }
        if (Integer.parseInt(modInvCount.getText()) < Integer.parseInt(modMin.getText())) {
            Inventory.alertInvMin();
            applicable = false;
        }
        if (Integer.parseInt(modMin.getText()) > Integer.parseInt(modMax.getText())) {
            Inventory.alertMinMax();
            applicable = false;
        }
        if (Integer.parseInt(modMax.getText()) < Integer.parseInt(modMin.getText())) {
            Inventory.alertMaxMin();
            applicable = false;
        }
        if (Double.parseDouble(modPrice.getText()) < 0) {
            Inventory.alertPriceNeg();
            applicable = false;
        }

        //Assigns values from TextFields
        if (applicable == true) {
            //New InHouse Part if radio button is checked
            if (radioInHouse.isSelected()) {
                int id = Integer.parseInt(modPartId.getText());
                String name = modPartName.getText();
                Double price = Double.parseDouble(modPrice.getText());
                int stock = Integer.parseInt(modInvCount.getText());
                int min = Integer.parseInt(modInvCount.getText());
                int max = Integer.parseInt(modMax.getText());
                int machineId = Integer.parseInt(companyName.getText());
                //Assigns values
                InHouse inHousePart = new InHouse(id,name,price,stock,min,max,machineId);
                inHousePart.setId(Integer.parseInt(modPartId.getText()));
                inHousePart.setName(modPartName.getText());
                inHousePart.setPrice(Double.parseDouble(modPrice.getText()));
                inHousePart.setStock(Integer.parseInt(modInvCount.getText()));
                inHousePart.setMax(Integer.parseInt(modMax.getText()));
                inHousePart.setMin(Integer.parseInt(modMin.getText()));
                inHousePart.setMachineId(Integer.parseInt(companyName.getText()));
                //updates part
                Inventory.updatePart(Integer.parseInt(modPartId.getText()) - 1, inHousePart);
            }
            //New Outsourced Part if radio button is checked
            if (radioOutsourced.isSelected()) {
                int id = Integer.parseInt(modPartId.getText());
                String name = modPartName.getText();
                Double price = Double.parseDouble(modPrice.getText());
                int stock = Integer.parseInt(modInvCount.getText());
                int min = Integer.parseInt(modInvCount.getText());
                int max = Integer.parseInt(modMax.getText());
                String machineId = companyName.getText();
                if (Inventory.isInt(companyName.getText())) {
                    applicable = false;
                }
                Outsourced outSourcedPart = new Outsourced(id,name,price,stock,min,max,machineId);
                //Assigns values
                outSourcedPart.setId(id);
                outSourcedPart.setName(name);
                outSourcedPart.setPrice(price);
                outSourcedPart.setStock(stock);
                outSourcedPart.setMax(max);
                outSourcedPart.setMin(min);
                outSourcedPart.setCompanyName(machineId);
                //updates part
                if (applicable) {
                    Inventory.updatePart(Integer.parseInt(modPartId.getText()) - 1, outSourcedPart);
                }
            }
        }

        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Main Screen");
        window.show();
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

    //Changes Labels and TextField Prompt to InHouse Type
    public void modInHouse() {
        if ((this.partGroup.getSelectedToggle().equals(this.radioInHouse))) {
            companyNameLabel.setText("Machine ID");
            companyName.setPromptText("Machine ID");
        }
    }

    //Changes Labels and TextField prompt to Outsourced Type
    public void modOutsourced() {
        if ((this.partGroup.getSelectedToggle().equals(this.radioOutsourced))) {
            companyNameLabel.setText("Company Name");
            companyName.setPromptText("Company Name");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedPart = (Part) MainScreen.getSelectedPart();
        modPartId.setText(Integer.toString(selectedPart.getId()));
        modPartName.setText(selectedPart.getName());
        modInvCount.setText(Integer.toString(selectedPart.getStock()));
        modPrice.setText(Double.toString(selectedPart.getPrice()));
        modMax.setText(Integer.toString(selectedPart.getMax()));
        modMin.setText(Integer.toString(selectedPart.getMin()));
        if (selectedPart instanceof InHouse) {
            radioInHouse.setSelected(true);
            companyName.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof Outsourced) {
            radioOutsourced.setSelected(true);
            companyName.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }
}
