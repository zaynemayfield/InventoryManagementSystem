package com.wgu.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class handles all parts and products
 * */

public class Inventory {
    //List of parts
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    //List of products
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partIdCount = 0;

    private static int productIdCount = 0;

    /**
     * @param newPart adds new part to allParts
     * */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * @param newProduct adds new product to allProducts
     * */
    public static void addProduct(Product newProduct){ allProducts.add(newProduct); }

    /**
     * @param partId Looks up a partId and returns the part
     * */
    public static Part lookupPart(int partId){
        Part searchedPart = null;
        for (Part part : allParts){
            if (part.getId() == partId){
                searchedPart = part;
                System.out.println("Found match");
            }
        }
        System.out.println("Return Part");
        return searchedPart;
    }

    /**
     * @param productId Looks up a productId and returns the product
     * */
    public static Product lookupProduct(int productId){
        Product searchedProduct = null;
        for (Product product : allProducts){
            if (product.getId() == productId){
                searchedProduct = product;
            }
        }
        return searchedProduct;
    }

    /**
     * @param partName Takes part name and looks up and returns part
     *                 Had a problem with searching. I was using equals instead of contains.
     * */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        for (Part part : allParts){
            if (part.getName().contains(partName)){
                searchedParts.add(part);
            }
        }
        return searchedParts;
    }

    /**
     * @param productName Takes product name and looks up and returns the product
     * */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if (product.getName().contains(productName)){
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }

    /**
     * @param selectedPart updates the part
     * */
    public static void updatePart(int index, Part selectedPart){

        allParts.set(index, selectedPart);
    }

    /**
     * @param selectedProduct updates the product
     * */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * @param selectedPart deletes selected part
     * */
    public static boolean deletePart(Part selectedPart){

        return allParts.removeIf(part -> part.equals(selectedPart));
    }

    /**
     * @param selectedProduct deletes selected product
     * */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.removeIf(product -> product.equals(selectedProduct));
    }

    /**
     * @return all parts
     * */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @return all products
     * */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    /**
     * @return next id for part
     * */
    public static int getNextPartId(){
        return ++partIdCount;
    }
    /**
     * @return next id for product
     * */
    public static int getNextProductId(){
        return ++productIdCount;
    }

}
