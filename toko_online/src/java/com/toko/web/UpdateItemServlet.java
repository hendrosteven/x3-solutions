/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.web;

import com.toko.dao.CategoryDAO;
import com.toko.dao.DbConnection;
import com.toko.dao.ItemDAO;
import com.toko.dao.impl.CategoryDAOImpl;
import com.toko.dao.impl.ItemDAOImpl;
import com.toko.model.Category;
import com.toko.model.Item;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "UpdateItemServlet", urlPatterns = {"/update_item"})
public class UpdateItemServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        int id = Integer.valueOf(request.getParameter("id"));
        int categoryId = Integer.valueOf(request.getParameter("category"));
        double price = Double.valueOf(request.getParameter("price"));
        String description = request.getParameter("description");
        String name = request.getParameter("name");
        try {
           DbConnection conn = new DbConnection();
           CategoryDAO cDao = new CategoryDAOImpl(conn.getConnection());
           Category cat = cDao.getById(categoryId);
           Item item = new Item(id, name, description, price, cat);
           ItemDAO dao = new ItemDAOImpl(conn.getConnection());
           dao.update(item);
           conn.closeConnection();
           out.print("true");
        }catch(Exception ex){
            ex.printStackTrace();
            out.print("false");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
