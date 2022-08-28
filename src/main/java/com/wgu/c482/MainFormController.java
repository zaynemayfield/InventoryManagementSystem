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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * The MainFormController class handles all the operations associated with MainForm.fxml.
 * Future Feature #2: I would add feature that checks if part is associated with any product and not allow the part to be deleted until it was removed from all products.
 *
 * */
public class MainFormController implements Initializable {
    @FXML
    private Label status;
    @FXML
    public TableView partTable, productTable;
    @FXML
    public TableColumn partIDCol, partNameCol, partNumStockCol, partPriceCol, productIDCol, productNameCol, productNumStockCol, productPriceCol;
    @FXML
    public TextField partSearch, productSearch;
    static private Inventory inventory = null;
    static boolean fakeData = true;
    private ObservableList<Part> showParts = FXCollections.observableArrayList();
    private ObservableList<Product> showProducts = FXCollections.observableArrayList();
    private Stage stage;
    private Scene scene;
    private Parent root;
    /**
     * Initializes the data for the tables and checks if the fake data has been inserted yet.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (fakeData){
            fakeData();
        }
        showParts = inventory.getAllParts();
        showProducts = inventory.getAllProducts();

        partTable.setItems(showParts);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partNumStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(showProducts);
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productNumStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

//        FilteredList<showParts> filteredParts = new FilteredList<>(showParts, b -> true);
//
//        partSearch.textProperty().addListener((observable -> oldValue, newValue) _>{
//            filteredParts.setPredicate(part -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                String lowerNewValue = newValue.toLowerCase();
//
//                if (showParts.get)
//            });
//        });

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
     * @param event gets the search queries and finds results.
     *              Checks if it is null, then checks if it is an integer and then searches either ID or name for a product.
     * */
    public void findProducts(KeyEvent event){
        String productSearchQuery = productSearch.getText();
        status.setText("Status: Searching for: " + productSearchQuery);
        if (productSearchQuery == ""){
            productTable.setItems(showProducts);
            status.setText("Status: Ready");
        } else {
            try{
                int productIdSearch = Integer.parseInt(productSearchQuery);
                ObservableList<Product> productSearchResultsId = FXCollections.observableArrayList();
                Product searchProduct = Inventory.lookupProduct(productIdSearch);
                if (searchProduct == null){
                    status.setText("Status: Your search for: " + productSearchQuery + " did not find a product. (ID)");
                    productTable.setItems(showProducts);
                } else {
                    productSearchResultsId.add(searchProduct);
                    productTable.setItems(productSearchResultsId);
                    status.setText("Status: Found results");
                    return;
                }
            } catch (NumberFormatException e) {
                status.setText("Status: Search Not Integer");
            }
            ObservableList<Product> productSearchResults = Inventory.lookupProduct(productSearchQuery);
            if (productSearchResults.isEmpty()) {
                status.setText("Status: Your search for: " + productSearchQuery + " did not find a product. (Name)");
                productTable.setItems(showProducts);
            } else {
                productTable.setItems(productSearchResults);
                status.setText("Status: Found results");
            }
        }
    }
    /**
     * This contains the fake data to populate the app
     * */
    private void fakeData(){
        inventory = new Inventory();

        InHouse part1 = new InHouse(1,"Ponies", 1.95, 1, 0, 100, 123423);
        Inventory.getNextPartId();
        InHouse part2 = new InHouse(2,"Zebras", 2.92, 20, 1, 24, 22342234);
        Inventory.getNextPartId();
        InHouse part3 = new InHouse(3,"Monsters", 3.54, 33, 1, 36, 3234234);
        Inventory.getNextPartId();

        Outsourced part4 = new Outsourced(4, "Pennies", 4.73, 40, 3, 43, "Company Pennies");
        Inventory.getNextPartId();
        Outsourced part5 = new Outsourced(5, "Zoiks", 5.21, 53, 2, 58, "Company Zoiks");
        Inventory.getNextPartId();
        Outsourced part6 = new Outsourced(6, "Monkeys", 6.05, 65, 5, 69, "Company Monkeys");
        Inventory.getNextPartId();


        Product product1 = new Product(1,"Animal Zoo",1.95,9,1,10);
        Inventory.getNextProductId();
        Product product2 = new Product(2,"Outer Space",2.93,109,2,289);
        Inventory.getNextProductId();
        Product product3 = new Product(3,"Ocean Life",3.98,187,3,389);
        Inventory.getNextProductId();

        product1.addAssociatedPart(part1);

        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);

        product3.addAssociatedPart(part1);
        product3.addAssociatedPart(part6);

        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        inventory.addPart(part4);
        inventory.addPart(part5);
        inventory.addPart(part6);

        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);

        fakeData = false;
    }
    /**
     * @param actionEvent sends user to the AddPartForm.fxml
     * */
    public void addPart(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param actionEvent this sends user to ModifyPartForm.fxml if selection is not null
     * */
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        if (partTable.getSelectionModel().getSelectedItem() == null){
            status.setText("Status: No part selected to modify");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartForm.fxml"));
        root = loader.load();
        ModifyPartFormController modifyPartFormController = loader.getController();
        if (partTable.getSelectionModel().getSelectedItem() instanceof InHouse){
            InHouse modifyPart = (InHouse) partTable.getSelectionModel().getSelectedItem();
            modifyPartFormController.getInHousePart(modifyPart);
        } else {
            Outsourced modifyPart = (Outsourced) partTable.getSelectionModel().getSelectedItem();
            modifyPartFormController.getOutsourcedPart(modifyPart);
        }

        scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param actionEvent deletes part with confirmation and checking for null
     * */
    public void deletePart(ActionEvent actionEvent) {
        Part deletePart = (Part) partTable.getSelectionModel().getSelectedItem();
        if (deletePart == null){
            status.setText("Status: No part selected to delete");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            alert.setTitle("Parts");
            alert.setHeaderText("Delete Confirmation");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (Inventory.deletePart(deletePart)){
                        status.setText("Status: Part deleted");
                    } else {
                        status.setText("Status: Failed to delete part");
                    }
                }
            });
        }
    }
    /**
     * @param actionEvent sends user to AddProductForm.fxml
     * */
    public void addProduct(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param actionEvent sends user to ModifyProductForm.fxml if not null
     * */
    public void modifyProduct(ActionEvent actionEvent) throws IOException {

        Product modifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if (modifyProduct == null){
            status.setText("Status: No product selected to modify");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductForm.fxml"));
        root = loader.load();
        ModifyProductFormController modifyProductFormController = loader.getController();
        modifyProductFormController.getProduct(modifyProduct);

        scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param actionEvent deletes product if it does not have any parts and the selected is not null as well as with a confirmation alert
     * */
    public void deleteProduct(ActionEvent actionEvent) {
        Product deleteProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if (deleteProduct == null){
            status.setText("Status: No product selected to delete");
            return;
        } else {
            if (deleteProduct.getAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
                alert.setTitle("Products");
                alert.setHeaderText("Delete Confirmation");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        if (Inventory.deleteProduct(deleteProduct)) {
                            status.setText("Status: Product deleted");
                        } else {
                            status.setText("Status: Failed to delete product");
                        }
                    }
                });
            } else {
                status.setText("Status: Failed to delete product because there are parts associated with it.");
            }
        }

    }
    /**
     * @param actionEvent exits the program with an alert confirmation.
     * */
    public void exitProgram(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        alert.setTitle("Exit Program");
        alert.setHeaderText("Exit Program Confirmation");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }


}
