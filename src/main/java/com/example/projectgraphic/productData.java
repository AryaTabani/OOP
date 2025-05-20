//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.projectgraphic;

import java.sql.Date;

public class productData {
    private Integer id;
    private String productId;
    private String productName;
    private String type;
    private Integer stock;
    private Double price;
    private String status;
    private String image;
    private Date date;
    private Integer quantity;

    public productData(Integer id, String productId, String productName, String type, Integer stock, Double price, String status, String image, Date date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }

    public productData(Integer id, String productId, String productName, String type, Integer quantity, Double price, String image, Date date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
    }

    public Integer getId() {
        return this.id;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getType() {
        return this.type;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getStatus() {
        return this.status;
    }

    public String getImage() {
        return this.image;
    }

    public Date getDate() {
        return this.date;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
