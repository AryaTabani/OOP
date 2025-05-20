//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.projectgraphic;

import java.sql.Date;

public class customersData {
    private Integer id;
    private Integer customerID;
    private Double total;
    private Date date;
    private String emUsername;
    private String RestaurantName;

    public customersData(Integer id, Integer customerID, Double total, Date date, String emUsername,String RestaurantName) {
        this.id = id;
        this.customerID = customerID;
        this.total = total;
        this.date = date;
        this.emUsername = emUsername;
        this.RestaurantName = RestaurantName;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getCustomerID() {
        return this.customerID;
    }

    public Double getTotal() {
        return this.total;
    }

    public Date getDate() {
        return this.date;
    }
    public String getResname(){return this.RestaurantName;}

    public String getEmUsername() {
        return this.emUsername;
    }
}
