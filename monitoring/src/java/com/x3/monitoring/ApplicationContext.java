/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import javax.sql.DataSource;
import org.zkoss.zul.Div;

/**
 *
 * @author Hendro Steven
 */
public class ApplicationContext extends Div {

    private Connection conn;
    DataSource ds = null;

    public ApplicationContext() {
    }

    protected Connection getConn()throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monitoring", "root", "admin");
//            if (ds == null) {
//                Context env = (Context) new InitialContext().lookup("java:comp/env");
//                ds = (DataSource)env.lookup("jdbc/monitoring");
//            }
            //this.conn = ds.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        
        return this.conn;
    }
}
