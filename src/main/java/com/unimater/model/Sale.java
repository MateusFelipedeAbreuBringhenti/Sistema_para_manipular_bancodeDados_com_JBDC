package com.unimater.model;

import java.sql.Timestamp;
import java.util.List;

public class Sale {

    private int id;
    private List<SaleItem> saleItems;
    private Timestamp insertAt;

    public Sale(int id, List<SaleItem> saleItems, Timestamp insertAt) {
        this.id = id;
        this.saleItems = saleItems;
        this.insertAt = insertAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    public Timestamp getInsertAt() {
        return insertAt;
    }

    public void setInsertAt(Timestamp insertAt) {
        this.insertAt = insertAt;
    }
}
