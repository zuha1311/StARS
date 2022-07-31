package com.example.producta.Controller;

public class AllProducts {

    String name, rate, productStatus, image;

    public AllProducts()
    {}


    public AllProducts(String name, String rate, String productStatus, String image) {
        this.name = name;
        this.rate = rate;
        this.productStatus = productStatus;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
