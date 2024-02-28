package com.jignesh.shopex;

public class CustomerStoreModel {
    String active_days;
    String address;
    String category;
    String email;
    String mobile;
    String shop_name;
    String shop_owner;
    String shop_logo;

    public CustomerStoreModel(){}

    public CustomerStoreModel(String active_days, String address, String category, String email, String mobile, String shop_name, String shop_owner, String shop_logo) {
        this.active_days = active_days;
        this.address = address;
        this.category = category;
        this.email = email;
        this.mobile = mobile;
        this.shop_name = shop_name;
        this.shop_owner = shop_owner;
        this.shop_logo = shop_logo;
    }

    public String getActiveDays() {
        return active_days;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getShopName() {
        return shop_name;
    }

    public String getShopOwner() {
        return shop_owner;
    }

    public String getShopLogo(){
        return shop_logo;
    }

    public void setActiveDays(String active_days) {
        this.active_days = active_days;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setShopName(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setShopOwner(String shop_owner) {
        this.shop_owner = shop_owner;
    }

    public void setShopLogo(String shop_logo){
        this.shop_logo = shop_logo;
    }
}