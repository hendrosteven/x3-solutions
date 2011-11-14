/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.mahasiswa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.Semester;

/**
 *
 * @author Retha@wii
 */
public class KhsJSON extends HttpServlet {
   
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

            String output = "";
            String task = request.getParameter("task");
            //HttpSession sesi = request.getSession();
            Mahasiswa mahasiswa = (Mahasiswa) sesi.getAttribute("currentMhs");
            Semester semester=new SemesterDAO().getCurrentSemester();
            RegistrasiMatakuliahDAO dao = new RegistrasiMatakuliahDAO();

            if(task.equals("CURRENT")){
                JSONObject json = new JSONObject();
                json.put("currentSemester", semester.getNama() + " " + semester.getTahunAjaran());
                json.put("noIndukMhs", mahasiswa.getNomorInduk());
                json.put("namaMhs", mahasiswa.getNama());
                output = json.toString();
            }
            else if(task.equals("LISTING")){
                output = dao.getMatakuliahMahasiswaJSONObject(mahasiswa,semester).toString();
                sesi.setAttribute("dataTotal", dao.getDataTotal().toString());
            }
            else if(task.equals("TOTAL")){
                output = (String)sesi.getAttribute("dataTotal");
                sesi.removeAttribute("dataTotal");
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(KhsJSON.class.getName()).log(Level.SEVERE, null, ex);
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
