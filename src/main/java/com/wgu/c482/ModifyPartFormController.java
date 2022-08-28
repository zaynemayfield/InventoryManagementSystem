package com.wgu.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The ModifyPartFormController class handles all the operations with ModifyPartForm.fxml
 * */
public class ModifyPartFormController {

    @FXML
    private Label partSourceLabel, status;

    @FXML
    private RadioButton partInHouseRadio, partOutsourcedRadio;

    @FXML
    private ToggleGroup partSource;

    @FXML
    private TextField partID, partName, partInventory, partCost, partMax, partMin, partSourceInput;

    InHouse inHousePart = null;
    Outsourced outsourcedPart = null;
    private boolean inHouse = true;
    private boolean originalInHouse = true;
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * @param part sets the fields to the values of the part for InHouse
     * */
    public void getInHousePart(InHouse part) {
        this.inHousePart = part;
        partID.setText(String.valueOf(part.getId()));
        partName.setText(part.getName());
        partInventory.setText(String.valueOf(part.getStock()));
        partCost.setText(String.valueOf(part.getPrice()));
        partMax.setText(String.valueOf(part.getMax()));
        partMin.setText(String.valueOf(part.getMin()));
        partSourceLabel.setText("Machine ID");
        partSourceInput.setText(String.valueOf(part.getMachineId()));
        partSource.selectToggle(partInHouseRadio);
    }
    /**
     * @param part sets the fields to the values of the part for Outsourced
     * */
    public void getOutsourcedPart(Outsourced part){
        this.outsourcedPart = part;
        partID.setText(String.valueOf(part.getId()));
        partName.setText(part.getName());
        partInventory.setText(String.valueOf(part.getStock()));
        partCost.setText(String.valueOf(part.getPrice()));
        partMax.setText(String.valueOf(part.getMax()));
        partMin.setText(String.valueOf(part.getMin()));
        partSourceLabel.setText("Company name");
        partSourceInput.setText(part.getCompanyName());
        partSource.selectToggle(partOutsourcedRadio);
        this.inHouse = false;
        this.originalInHouse = false;
    }

    /**
     * @param actionEvent checks for radio button and switches text and changes inHouse to true or false.
     *                    Updated it to clear the input when switching, if user switching back it should retain the old data.
     *                    Updating it to old value is failing. I had setExt to "" running no matter what... put it into else clause.
     * */
    public void getPartSource(ActionEvent actionEvent) {
        if(partInHouseRadio.isSelected()){
            partSourceLabel.setText("Machine ID");
            if (this.originalInHouse){
                status.setText("Status: Switching back to In-House");
                partSourceInput.setText(String.valueOf(inHousePart.getMachineId()));
            } else{
                partSourceInput.setText("");
            }
            inHouse = true;
        }
        else if(partOutsourcedRadio.isSelected()){
            partSourceLabel.setText("Company Name");
            if (!this.originalInHouse){
                status.setText("Status: Switching back to Outsourced");
                partSourceInput.setText(outsourcedPart.getCompanyName());
            } else {
                partSourceInput.setText("");
            }
            inHouse = false;
        }
    }
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
     * @param actionEvent saves the part by using the setters.
     *                    This was originally being deleted and creating a new part, but changed to use setters and just update the part.
     *                    Having troubles switching from In-House to Outsourced. Able to create new object, but having troubles deleting old one.
     *                    I had it as inHouse and outsourced, so I converted it back to part and then deleted it.
     * */
    public void savePart(ActionEvent actionEvent) throws IOException {
        //Change to use the Setters and update the object
        if(inHouse && originalInHouse){
            inHousePart.setName(partName.getText());
            inHousePart.setPrice(Double.valueOf(partCost.getText()));
            inHousePart.setStock(Integer.parseInt(partInventory.getText()));
            inHousePart.setMin(Integer.parseInt(partMin.getText()));
            inHousePart.setMax(Integer.parseInt(partMax.getText()));
            inHousePart.setMachineId(Integer.parseInt(partSourceInput.getText()));
        } else if (inHouse && !originalInHouse){
            InHouse part = new InHouse(
                    Integer.parseInt(partID.getText()),
                    partName.getText(),
                    Double.valueOf(partCost.getText()),
                    Integer.parseInt(partInventory.getText()),
                    Integer.parseInt(partMin.getText()),
                    Integer.parseInt(partMax.getText()),
                    Integer.parseInt(partSourceInput.getText())
            );
            Part deletePart = this.outsourcedPart;
            if (!Inventory.deletePart(deletePart)){
                status.setText("Status: Could not delete part to create new one when switching from Outsourced to In-House");
                return;
            }
            Inventory.addPart(part);

        } else if (!inHouse && !originalInHouse) {
            outsourcedPart.setName(partName.getText());
            outsourcedPart.setPrice(Double.valueOf(partCost.getText()));
            outsourcedPart.setStock(Integer.parseInt(partInventory.getText()));
            outsourcedPart.setMin(Integer.parseInt(partMin.getText()));
            outsourcedPart.setMax(Integer.parseInt(partMax.getText()));
            outsourcedPart.setCompanyName(partSourceInput.getText());

        } else if (!inHouse && originalInHouse) {
            Outsourced part = new Outsourced(
                    Integer.parseInt(partID.getText()),
                    partName.getText(),
                    Double.valueOf(partCost.getText()),
                    Integer.parseInt(partInventory.getText()),
                    Integer.parseInt(partMin.getText()),
                    Integer.parseInt(partMax.getText()),
                    partSourceInput.getText()
            );
            Part deletePart = this.inHousePart;
            if (!Inventory.deletePart(deletePart)){
                status.setText("Status: Could not delete part to create new one when switching from In-House to Outsourced");
                return;
            }
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
