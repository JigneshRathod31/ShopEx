package com.jignesh.shopex.models;

public class ProductModel {
    String product_name;
    String product_image;
    String product_description;
    String product_price;
    String product_quantity;
    String product_onboard;

    public ProductModel(){}

    public ProductModel(String product_name, String product_image, String product_description, String product_price, String product_quantity, String product_onboard) {
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.product_onboard = product_onboard;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getProductImage() {
        return product_image;
    }

    public void setProductImage(String product_image) {
        this.product_image = product_image;
    }

    public String getProductDescription() {
        return product_description;
    }

    public void setProductDescription(String product_description) {
        this.product_description = product_description;
    }

    public String getProductPrice() {
        return product_price;
    }

    public void setProductPrice(String product_price) {
        this.product_price = product_price;
    }

    public String getProductQuantity() {
        return product_quantity;
    }

    public void setProductQuantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProductOnboard() {
        return product_onboard;
    }

    public void setProductOnboard(String product_onboard) {
        this.product_onboard = product_onboard;
    }
}
