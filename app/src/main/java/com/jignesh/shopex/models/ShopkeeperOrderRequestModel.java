package com.jignesh.shopex.models;

public class ShopkeeperOrderRequestModel {
    String customer_name;
    String customer_address;
    String product_name;
    String product_quantity;
    String delivery_status;
    String order_request_id;

    public ShopkeeperOrderRequestModel(){}

    public ShopkeeperOrderRequestModel(String customer_name, String customer_address, String product_name, String product_quantity, String delivery_status, String order_request_id) {
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.delivery_status = delivery_status;
        this.order_request_id = order_request_id;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomerAddress() {
        return customer_address;
    }

    public void setCustomerAddress(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getProductQuantity() {
        return product_quantity;
    }

    public void setProductQuantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getDeliveryStatus() {
        return delivery_status;
    }

    public void setDeliveryStatus(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getOrderRequestId() {
        return order_request_id;
    }

    public void setOrderRequestId(String order_request_id) {
        this.order_request_id = order_request_id;
    }
}
