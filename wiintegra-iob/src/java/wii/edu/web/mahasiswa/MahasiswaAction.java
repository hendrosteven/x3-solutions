/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.mahasiswa;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Retha@wii
 */
public class MahasiswaAction extends HttpServlet {
   
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
                if(!role.equals("mahasiswa")){
                    response.sendRedirect("logout");
                    return;
                }
            }

            String action = request.getParameter("action");

            if(action != null){
                if(action.equals("registrasi"))
                    request.getRequestDispatcher("page/mahasiswa/registrasi_matakuliah.jsp").forward(request, response);
                else if(action.equals("krs"))
                    request.getRequestDispatcher("page/mahasiswa/kst.jsp").forward(request, response);
                else if(action.equals("khs"))
                    request.getRequestDispatcher("page/mahasiswa/khs.jsp").forward(request, response);
                else if(action.equals("transkrip"))
                    request.getRequestDispatcher("page/mahasiswa/transkrip.jsp").forward(request, response);
                else if(action.equals("password"))
                    request.getRequestDispatcher("page/mahasiswa/ganti_password.jsp").forward(request, response);
                else
                    request.getRequestDispatcher("page/main.jsp").forward(request, response);
            }
            else{
                request.getRequestDispatcher("page/main.jsp").forward(request, response);
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
