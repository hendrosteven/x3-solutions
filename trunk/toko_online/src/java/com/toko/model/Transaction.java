/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class ini merupakan representasi dari tabel Transaction
 * @author user
 */
public class Transaction {
    private int id;
    private Date transactionDate;
    private String transactionNumber;
    private Customer customer;
    private List<TransactionDetail> details = new ArrayList<TransactionDetail>();
    
    public Transaction(int id,Date transDate, 
            String transNumber,Customer customer){
        this.id = id;
        this.transactionDate = transDate;
        this.transactionNumber = transNumber;
        this.customer = customer;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return the transactionNumber
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * @param transactionNumber the transactionNumber to set
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the details
     */
    public List<TransactionDetail> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }
    
    
}
