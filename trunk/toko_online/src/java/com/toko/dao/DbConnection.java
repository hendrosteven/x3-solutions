/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author user
 */
public class DbConnection {

    String url = "jdbc:mysql://localhost:3306/toko_online";
    String user = "root";
    String pass = "admin";
    Connection conn = null;

    public DbConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public Connection getConnection(){
        return this.conn;
    }
    
    public void closeConnection()throws Exception{
        try{
            if(this.conn!=null){
                this.conn.close();
            }
        }catch(Exception ex){
            throw ex;
        }
    }
}
