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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "SaveItemServlet", urlPatterns = {"/save_item"})
public class SaveItemServlet extends HttpServlet {

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
        String name = request.getParameter("itemname");
        String description = request.getParameter("description");
        double price = Double.valueOf(request.getParameter("price"));
        int category = Integer.valueOf(request.getParameter("category"));
        
        try{
            DbConnection conn = new DbConnection();
            ItemDAO dao = new ItemDAOImpl(conn.getConnection());
            CategoryDAO cDao = new CategoryDAOImpl(conn.getConnection());
            Category cat = cDao.getById(category);
            Item item = new Item();
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setCategory(cat);
            dao.insert(item);
            
            response.sendRedirect("items");
        }catch(Exception ex){
            ex.printStackTrace();
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
