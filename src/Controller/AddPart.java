package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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

public class AddPart implements Initializable {
    @FXML private TextArea partId;
    @FXML private TextArea partName;
    @FXML private TextArea invCount;
    @FXML private TextArea partPrice;
    @FXML private TextArea minPrice;
    @FXML private TextArea maxPrice;
    @FXML private RadioButton radioInHouse;
    @FXML private ToggleGroup partGroup;
    @FXML private RadioButton radioOutsourced;
    @FXML private TextArea companyName;
    @FXML private Label companyNameLabel;
    @FXML private int newPartId = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId()+1;
    private String company;
    private int machineId;

    @FXML public void saveButton(ActionEvent event) throws IOException {
        addPart();
        Parent projectParent = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        Scene projectScene = new Scene(projectParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(projectScene);
        window.setTitle("Main Screen");
        window.show();
    }

    public void addPart() {
        Part newPart = null;
        if (radioInHouse.isSelected()) {
            newPart = new InHouse(newPartId, partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(invCount.getText()), Integer.parseInt(minPrice.getText()), Integer.parseInt(maxPrice.getText()), Integer.parseInt(companyName.getText()));
        }
        if (radioOutsourced.isSelected()) {
            newPart = new Outsourced(newPartId, partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(invCount.getText()), Integer.parseInt(minPrice.getText()), Integer.parseInt(maxPrice.getText()), companyName.getText());

        }
        Boolean applicable = true;
        if (companyName.getText().isEmpty()     ||
                partName.getText().isEmpty()    ||
                invCount.getText().isEmpty()    ||
                partPrice.getText().isEmpty()   ||
                maxPrice.getText().isEmpty()    ||
                minPrice.getText().isEmpty()) {
            Inventory.alertEmptyField();
            applicable = false;
        }
        if (Integer.parseInt(invCount.getText()) > Integer.parseInt(maxPrice.getText())) {
            Inventory.alertInvMax();
            applicable = false;
        }
        if (Integer.parseInt(invCount.getText()) < Integer.parseInt(minPrice.getText())) {
            Inventory.alertInvMin();
            applicable = false;
        }
        if (Double.parseDouble(minPrice.getText()) > Integer.parseInt(maxPrice.getText())) {
            Inventory.alertMinMax();
            applicable = false;
        }
        if (Integer.parseInt(maxPrice.getText()) < Integer.parseInt(minPrice.getText())) {
            Inventory.alertMaxMin();
            applicable = false;
        }
        if (Integer.parseInt(partPrice.getText()) < 0) {
            Inventory.alertPriceNeg();
            applicable = false;
        }

        if ((this.partGroup.getSelectedToggle().equals(this.radioInHouse))) {
            machineId = Integer.parseInt(companyName.getText());
        }
        if ((this.partGroup.getSelectedToggle().equals(this.radioOutsourced))) {
            company = companyName.getText();
            if (Inventory.isInt(company)) {
                applicable = false;
            }
        }
        if (applicable == true) {
            Inventory.addPart(newPart);
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

    //Change Label and TextField to InHouse Type
    public void addInHouse() {
        if ((this.partGroup.getSelectedToggle().equals(this.radioInHouse))) {
            companyNameLabel.setText("Machine ID");
            companyName.setPromptText("Machine ID");
        }
    }

    //Change Label and TextField to Outsourced Type
    public void addOutsourced() {
        if ((this.partGroup.getSelectedToggle().equals(this.radioOutsourced))) {
            companyNameLabel.setText("Company Name");
            companyName.setPromptText("Company Name");
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
