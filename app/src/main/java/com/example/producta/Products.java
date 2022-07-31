package com.example.producta;

public class Products {

    String name, description, rate, sport, image, pid, period, productStatus;

    public Products()
    {

    }

    public Products(String name, String description, String rate, String sport, String image, String pid, String period, String productStatus) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.sport = sport;
        this.image = image;
        this.pid = pid;
        this.period = period;
        this.productStatus = productStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}
