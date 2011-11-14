/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Hendro Steven
 */
public class ResultSetTableModel extends AbstractTableModel {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;

    // initialize resultSet and obtain its meta data object;
    // determine number of rows
    public ResultSetTableModel(Connection connection,
            String query) throws SQLException, ClassNotFoundException {
        // load database driver class
        this.connection = connection;
        // create Statement to query database
        statement = this.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        // set query and execute it
        setQuery(query);
    }

    // get class that represents column type
    public Class getColumnClass(int column) {
        // determine Java class of column
        try {
            String className =
                    metaData.getColumnClassName(column + 1);

            // return Class object that represents className
            return Class.forName(className);
        } // catch SQLExceptions and ClassNotFoundExceptions
        catch (Exception exception) {
            exception.printStackTrace();
        }

        // if problems occur above, assume type Object
        return Object.class;
    }

    // get number of columns in ResultSet
    public int getColumnCount() {
        // determine number of columns
        try {
            return metaData.getColumnCount();
        } // catch SQLExceptions and print error message
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // if problems occur above, return 0 for number of columns
        return 0;
    }

// get name of a particular column in ResultSet
    public String getColumnName(int column) {
        // determine column name
        try {
            return metaData.getColumnName(column + 1);
        } // catch SQLExceptions and print error message
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // if problems, return empty string for column name
        return "";
    }

    // return number of rows in ResultSet
    public int getRowCount() {
        return numberOfRows;
    }

    // obtain value in particular row and column
    public Object getValueAt(int row, int column) {
        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } // catch SQLExceptions and print error message
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // if problems, return empty string object
        return "";
    }

    protected void finalize() {
        // close Statement and Connection
        try {
            statement.close();
            connection.close();
        } // catch SQLExceptions and print error message
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    // set new database query string
    public void setQuery(String query) throws SQLException {
        // specify query and execute it
        resultSet = statement.executeQuery(query);

        // obtain meta data for ResultSet
        metaData = resultSet.getMetaData();

        // determine number of rows in ResultSet
        resultSet.last(); // move to last row
        numberOfRows = resultSet.getRow(); // get row number

        // notify JTable that model has changed
        fireTableStructureChanged();
    }
} // end class ResultSetTa
