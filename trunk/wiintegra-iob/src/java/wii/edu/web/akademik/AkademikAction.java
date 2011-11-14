/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

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
public class AkademikAction extends HttpServlet {
   
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
                    if(user.getLevel() != 2){
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
                if(action.equals("angkatan"))
                    request.getRequestDispatcher("page/akademik/angkatan.jsp").forward(request, response);
                else if(action.equals("semester"))
                    request.getRequestDispatcher("page/akademik/semester.jsp").forward(request, response);
                else if(action.equals("jadwal"))
                    request.getRequestDispatcher("page/akademik/jadwal.jsp").forward(request, response);
                else if(action.equals("mahasiswa"))
                    request.getRequestDispatcher("page/akademik/mahasiswa.jsp").forward(request, response);
                else if(action.equals("mahasiswareg"))
                    request.getRequestDispatcher("page/akademik/mahasiswa_reg.jsp").forward(request, response);
                else if(action.equals("mahasiswaunreg"))
                    request.getRequestDispatcher("page/akademik/mahasiswa_unreg.jsp").forward(request, response);
                else if(action.equals("ips"))
                    request.getRequestDispatcher("page/akademik/ips.jsp").forward(request, response);
                else if(action.equals("ipk"))
                    request.getRequestDispatcher("page/akademik/ipk.jsp").forward(request, response);
                else if(action.equals("krs"))
                    request.getRequestDispatcher("page/akademik/kst.jsp").forward(request, response);
                else if(action.equals("kelas"))
                    request.getRequestDispatcher("page/akademik/kelas.jsp").forward(request, response);
                else if(action.equals("nilai"))
                    request.getRequestDispatcher("page/akademik/nilai.jsp").forward(request, response);
                else if(action.equals("kelulusan"))
                    request.getRequestDispatcher("page/akademik/kelulusan.jsp").forward(request, response);
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
