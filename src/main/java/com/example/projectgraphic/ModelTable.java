//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.projectgraphic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ModelTable {
    private final SimpleStringProperty menuname;
    private final SimpleIntegerProperty menuid;
    private final SimpleIntegerProperty quantity_item;
    private final SimpleIntegerProperty price;

    public ModelTable(String menuname, int menuid, int quantity_item, int price) {
        this.menuname = new SimpleStringProperty(menuname);
        this.menuid = new SimpleIntegerProperty(menuid);
        this.quantity_item = new SimpleIntegerProperty(quantity_item);
        this.price = new SimpleIntegerProperty(price);
    }

    public String getMenuname() {
        return this.menuname.get();
    }

    public void setMenuname(String menuname) {
        this.menuname.set(menuname);
    }

    public int getMenuid() {
        return this.menuid.get();
    }

    public void setMenuid(int menuid) {
        this.menuid.set(menuid);
    }

    public int getQuantity_item() {
        return this.quantity_item.get();
    }

    public void setQuantity_item(int quantity_item) {
        this.quantity_item.set(quantity_item);
    }

    public int getPrice() {
        return this.price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
}
