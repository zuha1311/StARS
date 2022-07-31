package com.example.producta.Controller;

public class Cart {

    private String pid,name,price, image, period;

    public Cart()
    {}


    public Cart(String pid, String name, String price, String period, String image) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.image = image;
        this.period = period;
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}
