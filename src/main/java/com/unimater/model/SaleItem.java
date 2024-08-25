package com.unimater.model;

public class SaleItem {
    private int id;
    private Product product;
    private int quantity;
    private double percentualDiscount;

    public SaleItem(int id, Product product, int quantity, double percentualDiscount) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.percentualDiscount = percentualDiscount;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPercentualDiscount() {
        return percentualDiscount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPercentualDiscount(double percentualDiscount) {
        this.percentualDiscount = percentualDiscount;
    }
}
