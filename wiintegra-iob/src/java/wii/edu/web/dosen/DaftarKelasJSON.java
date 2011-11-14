/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.dosen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Dosen;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;

/**
 *
 * @author Retha@wii
 */
public class DaftarKelasJSON extends HttpServlet {
   
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
                if(!role.equals("dosen")){
                    response.sendRedirect("logout");
                    return;
                }
            }

            String output = "";

            Semester semester=new SemesterDAO().getCurrentSemester();
            //HttpSession sesi = request.getSession();
            Dosen dosen=(Dosen)sesi.getAttribute("currentDosen");
            String task = request.getParameter("task");

            if(task.equals("JADWAL"))
                output = new JadwalDAO().getJadwalJSONObject(semester,dosen).toString();
            else if(task.equals("CURRENT")){
                output = "Semester " + semester.getNama() + " Tahun Ajaran " + semester.getTahunAjaran();
            }
            else if(task.equals("MAHASISWA_LIMIT")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                output = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliahJSONObject(new JadwalDAO().getJadwal(Long.parseLong(request.getParameter("id"))), start, limit).toString();
            }
            else if(task.equals("UPDATE")){
                long id = Long.parseLong(request.getParameter("id"));
                String nilai = request.getParameter("nilai");

                RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(id);
                reg.setNilai(nilai);
                output = Integer.toString(new ObjectDAO().update(reg));
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(DaftarKelasJSON.class.getName()).log(Level.SEVERE, null, ex);
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
