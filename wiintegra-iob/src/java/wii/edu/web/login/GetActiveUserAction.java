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
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class GetActiveUserAction extends HttpServlet {
   
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
            
            String output = "";
            String task = request.getParameter("task");

            if(task.equals("LOGIN_DATA")){
                //HttpSession sesi = request.getSession();

                String role = (String)sesi.getAttribute("role");

                if(role.equals("user")){
                    User user = (User)sesi.getAttribute("currentUser");
                    String level = user.getLevel() == 1 ? "Admin" : "Akademik";
                    output = "<br>&nbsp;&nbsp;<b>" + user.getRealName() + "</b><br>&nbsp;&nbsp;[ " + level + " ]";
                }
                else if(role.equals("dosen")){
                    Dosen dosen = (Dosen)sesi.getAttribute("currentDosen");
                    output = "<br>&nbsp;&nbsp;<b>" + dosen.getNama() + "</b><br>&nbsp;&nbsp;No. Peg. : " + dosen.getNomorPegawai();
                }
                else if(role.equals("mahasiswa")){
                    Mahasiswa mhs = (Mahasiswa)sesi.getAttribute("currentMhs");
                    output = "<br>&nbsp;&nbsp;<b>" + mhs.getNama() + "</b><br>&nbsp;&nbsp;NIM : " + mhs.getNomorInduk() + "<br><br>" +
                             "&nbsp;&nbsp;Fakultas " + mhs.getFakultas().getNama() + "<br>" +
                             "&nbsp;&nbsp;Progdi " + mhs.getProgdi().getNama();
                }
            }
            System.out.println(output);
            out.println(output);
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
