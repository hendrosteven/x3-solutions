/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import wii.edu.core.dao.DosenDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.UserDAO;
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class Logout extends HttpServlet {
   
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
            HttpSession session = request.getSession();

            if((String)session.getAttribute("role") == null){
                request.setAttribute("error", "Anda harus login terlebih dulu!");
                request.getRequestDispatcher("/page/index.jsp").include(request, response);
                return;
            }
            String role = (String)session.getAttribute("role");

            if(role.equals("user")){
                User user = (User)session.getAttribute("currentUser");
                try{
                    new UserDAO().logout(user);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else if(role.equals("mahasiswa")){
                Mahasiswa mhs = (Mahasiswa)session.getAttribute("currentMhs");
                try{
                    new MahasiswaDAO().logout(mhs);
                    System.out.println("");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else if(role.equals("dosen")){
                Dosen dosen = (Dosen)session.getAttribute("currentDosen");
                try{
                    new DosenDAO().logout(dosen);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            session.invalidate();
            request.getRequestDispatcher("/page/index.jsp").include(request, response);
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
