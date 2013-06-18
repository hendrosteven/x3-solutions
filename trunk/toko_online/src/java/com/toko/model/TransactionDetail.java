/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.model;

/**
 *
 * @author user
 */
public class TransactionDetail {
    private Item item;
    private int quantity;
    private double price;
    
    public TransactionDetail(Item item, int quantity,
            double price){
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
