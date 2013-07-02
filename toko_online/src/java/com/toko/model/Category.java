/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.model;

/**
 * Class ini merupakan representasi dari tabel categories
 * @author user
 */
public class Category {
    private int id;
    private String name;
    
    public Category(){
        
    }
    
    public Category(int id,String name){
        this. id = id;
        this.name = name;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
           
}
