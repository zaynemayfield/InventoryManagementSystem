package com.wgu.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * AddPartFormController class deals with everything on the AddPartForm.fxml which deals with creating parts.
 * Need to check for numeric values
 * */
public class AddPartFormController {

    @FXML
    private Label partSourceLabel, status;
    @FXML
    private RadioButton partInHouseRadio, partOutsourcedRadio;
    @FXML
    private TextField partID, partName, partInventory, partCost, partMax, partMin, partSourceInput;

    private boolean inHouse = true;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * @param actionEvent checks to make sure the input fields are not null.
     *                    Added functionality to check if the inputs are integers and double.
     * */
    public void checkNull(ActionEvent actionEvent) throws IOException {
        if (partName.getText() == ""){
            status.setText("Status: Name is blank");
            return;
        } else if (partInventory.getText() ==  "") {
            status.setText("Status: Inventory is blank");
            return;
        } else if (partCost.getText() ==  "") {
            status.setText("Status: Price/Cost is blank");
            return;
        } else if (partMax.getText() ==  "") {
            status.setText("Status: Max is blank");
            return;
        } else if (partMin.getText() ==  "") {
            status.setText("Status: Min is blank");
            return;
        } else if (partSourceInput.getText() ==  "") {
            if (inHouse){
                status.setText("Status: Machine ID is blank");
            } else {
                status.setText("Status: Company Name is blank");
            }
            return;
        } else {
            //check inv, max, min for integer
            try {
                int inv = Integer.parseInt(partInventory.getText());
                int max = Integer.parseInt(partMax.getText());
                int min = Integer.parseInt(partMin.getText());
                if (!checkInventory(inv, max, min)) {
                    return;
                }
            } catch (NumberFormatException e) {
                status.setText("Status: Inv, Max, or Min is not a integer");
                return;
            }
        }
        // Check Price cost for Double
        try {
            Double.parseDouble(partCost.getText());
        } catch (NumberFormatException e){
            status.setText("Status: Price/Cost is not a decimal number");
            return;
        }
        if (inHouse) {
            try {
                Integer.parseInt(partSourceInput.getText());
            } catch (NumberFormatException e){
                status.setText("Status: Machine ID is not an integer");
                return;
            }
        }
        savePart(actionEvent);
    }
    /**
     * Checks to make sure the inventory, max and min are set correctly.
     * */
    public boolean checkInventory(int inv, int max, int min){
        if(inv > max) {
            status.setText("Status: Inventory greater than max");
            return false;
        } else if (inv < min) {
            status.setText("Status: Inventory less than min");
            return false;
        } else {
            return true;
        }
    }
    /**
     * @param actionEvent saves the part as either InHouse or Outsourced
     * */
    public void savePart(ActionEvent actionEvent) throws IOException {
        if(inHouse){
            InHouse part = new InHouse(
                    Inventory.getNextPartId(),
                    partName.getText(),
                    Double.valueOf(partCost.getText()),
                    Integer.parseInt(partInventory.getText()),
                    Integer.parseInt(partMin.getText()),
                    Integer.parseInt(partMax.getText()),
                    Integer.parseInt(partSourceInput.getText())
            );
            Inventory.addPart(part);
        } else {
            Outsourced part = new Outsourced(
                    Inventory.getNextPartId(),
                    partName.getText(),
                    Double.valueOf(partCost.getText()),
                    Integer.parseInt(partInventory.getText()),
                    Integer.parseInt(partMin.getText()),
                    Integer.parseInt(partMax.getText()),
                    partSourceInput.getText()
            );
            Inventory.addPart(part);
        }
        goToMain(actionEvent);
    }
    /**
     * @param actionEvent goes to the main form
     * */
    public void cancelPart(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }
    /**
     * @param actionEvent checks for radio button and switches text and changes inHouse to true or false.
     * */
    public void getPartSource(ActionEvent actionEvent) {
        if(partInHouseRadio.isSelected()){
            partSourceLabel.setText("Machine ID");
            inHouse = true;
        }
        else if(partOutsourcedRadio.isSelected()){
            partSourceLabel.setText("Company Name");
            inHouse = false;
        }
    }
    /**
     * @param actionEvent goes to the main form
     * */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
