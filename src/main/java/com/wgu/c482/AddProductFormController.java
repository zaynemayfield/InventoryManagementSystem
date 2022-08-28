package com.wgu.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** AddProductFormController class deals with everything on the AddProductForm.fxml which deals with creating products */
public class AddProductFormController implements Initializable {
    @FXML
    private Label status;
    @FXML
    public TableView partTable, associatedPartTable;
    @FXML
    public TableColumn partIDCol, partNameCol, partNumStockCol, partPriceCol, associatedPartIDCol, associatedPartNameCol, associatedPartNumStockCol, associatedPartPriceCol;
    @FXML
    private TextField productID, productName, productInventory, productCost, productMax, productMin, partSearch;
    private ObservableList<Part> showParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    static private Inventory inventory = null;
    static private Product product = null;

    private Stage stage;
    private Scene scene;
    private Parent root;
    /**
     * @param actionEvent checks to make sure the input fields are not null.
     *                    Added functionality to check if the inputs are integers and double.
     * */
    public void checkNull(ActionEvent actionEvent) throws IOException {
        if (productName.getText() == ""){
            status.setText("Status: Name is blank");
            return;
        } else if (productInventory.getText() ==  "") {
            status.setText("Status: Inventory is blank");
            return;
        } else if (productCost.getText() ==  "") {
            status.setText("Status: Price/Cost is blank");
            return;
        } else if (productMax.getText() ==  "") {
            status.setText("Status: Max is blank");
            return;
        } else if (productMin.getText() ==  "") {
            status.setText("Status: Min is blank");
            return;
        }
//        else if(associatedParts.isEmpty()){
//            status.setText("Status: No parts associated with this product");
//            return;
//        }
        else {
            //check inv, max, min for integer
            try {
                int inv = Integer.parseInt(productInventory.getText());
                int max = Integer.parseInt(productMax.getText());
                int min = Integer.parseInt(productMin.getText());


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
            Double.parseDouble(productCost.getText());
        } catch (NumberFormatException e){
            status.setText("Status: Price/Cost is not a decimal number");
            return;
        }
        saveProduct(actionEvent);
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
     * @param actionEvent Adds part to product if not null.
     * */
    public void addPartToProduct(ActionEvent actionEvent) {
        Part addPart = (Part) partTable.getSelectionModel().getSelectedItem();
        if (addPart == null){
            status.setText("Status: No part selected to Add");
        } else{
            associatedParts.add(addPart);
            status.setText("Status: Part added to product");
        }
        return;

    }
    /**
     * @param actionEvent removes the part from the product if not null.
     * */
    public void removePartFromProduct(ActionEvent actionEvent) {
        Part deletePart = (Part) associatedPartTable.getSelectionModel().getSelectedItem();
        if (deletePart == null){
            status.setText("Status: No part selected to remove");
            return;
        }
        if (associatedParts.removeIf(part->part.equals(deletePart))) {
            status.setText("Status: Part removed");
        } else {
            status.setText("Status: Failed to remove part");
        }
    }
    /**
     * @param actionEvent saves the product and goes to the main form.
     * */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        Product product = new Product(
                Inventory.getNextProductId(),
                productName.getText(),
                Double.valueOf(productCost.getText()),
                Integer.valueOf(productInventory.getText()),
                Integer.valueOf(productMin.getText()),
                Integer.valueOf(productMax.getText())
        );
        for (Part associatedPart : associatedParts){
            product.addAssociatedPart(associatedPart);
        }
        Inventory.addProduct(product);
        goToMain(actionEvent);

    }
    /**
     * @param actionEvent goes to the main form
     * */
    public void cancelProduct(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }
    /**
     * Loads the data into the tables.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showParts = inventory.getAllParts();

        partTable.setItems(showParts);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partNumStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartNumStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * @param event gets the search queries and finds results.
     *              Checks if it is null, then checks if it is an integer and then searches either ID or name for a part.
     * */
    public void findParts(KeyEvent event){
        String partSearchQuery = partSearch.getText();
        status.setText("Status: Searching for: " + partSearchQuery);
        if (partSearchQuery == ""){
            partTable.setItems(showParts);
            status.setText("Status: Ready");
        } else {
            try{
                int partIdSearch = Integer.parseInt(partSearchQuery);
                ObservableList<Part> partSearchResultsId = FXCollections.observableArrayList();
                Part searchPart = Inventory.lookupPart(partIdSearch);
                if (searchPart == null){
                    status.setText("Status: Your search for: " + partSearchQuery + " did not find a part. (ID)");
                    partTable.setItems(showParts);
                } else {
                    partSearchResultsId.add(searchPart);
                    partTable.setItems(partSearchResultsId);
                    status.setText("Status: Found results");
                    return;
                }
            } catch (NumberFormatException e) {
                status.setText("Status: Search Not Integer");
            }
            ObservableList<Part> partSearchResults = Inventory.lookupPart(partSearchQuery);
            if (partSearchResults.isEmpty()) {
                status.setText("Status: Your search for: " + partSearchQuery + " did not find a part. (Name)");
                partTable.setItems(showParts);
            } else {
                partTable.setItems(partSearchResults);
                status.setText("Status: Found results");
            }
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
