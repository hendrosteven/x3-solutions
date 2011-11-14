/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class AdminAction extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesi = request.getSession();
            if(sesi.getAttribute("role") == null){
                request.getRequestDispatcher("page/index.jsp").forward(request, response);
                return;
            }
            else{
                String role = (String)sesi.getAttribute("role");
                if(role.equals("user")){
                    User user = (User)sesi.getAttribute("currentUser");
                    if(user.getLevel() != 1){
                        response.sendRedirect("logout");
                        return;
                    }
                }
                else{
                        response.sendRedirect("logout");
                    return;
                }
            }

            String action = request.getParameter("action");

            if(action != null){
                if(action.equals("user"))
                    request.getRequestDispatcher("page/admin/user.jsp").include(request, response);
                else if(action.equals("fakultas"))
                    request.getRequestDispatcher("page/admin/fakultas.jsp").include(request, response);
                else if(action.equals("progdi"))
                    request.getRequestDispatcher("page/admin/progdi.jsp").include(request, response);
                else if(action.equals("dosen"))
                    request.getRequestDispatcher("page/admin/dosen.jsp").include(request, response);
                else if(action.equals("matakuliah"))
                    request.getRequestDispatcher("page/admin/matakuliah.jsp").include(request, response);
                else if(action.equals("ruang"))
                    request.getRequestDispatcher("page/admin/ruang.jsp").include(request, response);
                else if(action.equals("distrik"))
                    request.getRequestDispatcher("page/admin/distrik.jsp").include(request, response);
                else if(action.equals("ERROR") && sesi.getAttribute("error") != null){
                    out.println(sesi.getAttribute("error"));
                    sesi.removeAttribute("error");
                }
                else
                    request.getRequestDispatcher("page/main.jsp").include(request, response);
            }
            else{
                request.getRequestDispatcher("page/main.jsp").include(request, response);
            }

            if(request.getParameter("error") != null){
                System.out.println("tulis error");
                sesi.setAttribute("error", "Maaf error!!");
            }
            
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
