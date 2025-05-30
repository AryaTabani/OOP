//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.projectgraphic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ModelTable1 {
    private final SimpleIntegerProperty orderno;
    private final SimpleStringProperty menuname;
    private final SimpleIntegerProperty quantity_item;
    private final SimpleStringProperty status;

    public ModelTable1(int orderno, String menuname, int quantity_item, String status) {
        this.menuname = new SimpleStringProperty(menuname);
        this.orderno = new SimpleIntegerProperty(orderno);
        this.quantity_item = new SimpleIntegerProperty(quantity_item);
        this.status = new SimpleStringProperty(status);
    }

    public String getMenuname() {
        return this.menuname.get();
    }

    public void setMenuname(String menuname) {
        this.menuname.set(menuname);
    }

    public int getOrderno() {
        return this.orderno.get();
    }

    public void setOrderno(int menuid) {
        this.orderno.set(menuid);
    }

    public int getQuantity_item() {
        return this.quantity_item.get();
    }

    public void setQuantity_item(int quantity_item) {
        this.quantity_item.set(quantity_item);
    }

    public String getStatus() {
        return this.status.get();
    }

    public void setPrice(String status) {
        this.status.set(status);
    }
}
