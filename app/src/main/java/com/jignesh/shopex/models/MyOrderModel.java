package com.jignesh.shopex.models;

public class MyOrderModel {
    String product_name;
    String product_image;
    String product_description;
    String product_price;
    String product_quantity;
    String product_onboard;
    String order_date;
    String order_quantity;
    String shop_name;
    String delivery_status;

    public MyOrderModel(){}

    public MyOrderModel(String product_name, String product_image, String product_description, String product_price, String product_quantity, String product_onboard, String order_date, String order_quantity, String shop_name, String delivery_status) {
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.product_onboard = product_onboard;
        this.order_date = order_date;
        this.order_quantity = order_quantity;
        this.shop_name = shop_name;
        this.delivery_status = delivery_status;
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

    public String getOrderDate() {
        return order_date;
    }

    public void setOrderDate(String order_date) {
        this.order_date = order_date;
    }

    public String getOrderQuantity() {
        return order_quantity;
    }

    public void setOrderQuantity(String order_quantity) {
        this.order_quantity = order_quantity;
    }

    public String getShopName() {
        return shop_name;
    }

    public void setShopName(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getDeliveryStatus() {
        return delivery_status;
    }

    public void setDeliveryStatus(String delivery_status) {
        this.delivery_status = delivery_status;
    }
}
