/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

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
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.MatakuliahDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class NilaiJSON extends HttpServlet {
   
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
            String output = "";

            //HttpSession sesi = request.getSession();
            String task = request.getParameter("task");

            if(task.equals("LISTING")){
                long id = Long.parseLong(request.getParameter("idMahasiswa"));
                Mahasiswa mhs = new MahasiswaDAO().getMahasiswa(id);
                output = new RegistrasiMatakuliahDAO().getAllMatakuliahMahasiswaJSONObject(mhs).toString();
                sesi.setAttribute("mahasiswa",mhs);

            }
            else if(task.equals("MAHASISWA")){
                output = new MahasiswaDAO().getAllMahasiswaBelumLulusJSONObject().toString();
            }
            else if(task.equals("MATKUL")){
                long id = Long.parseLong(request.getParameter("idMahasiswa"));
                Mahasiswa mhs = new MahasiswaDAO().getMahasiswa(id);
                output = new RegistrasiMatakuliahDAO().getAllMatakuliahMahasiswaSisaJSONObject(mhs).toString();
            }
            else if(task.equals("CREATE")){
                Mahasiswa mahasiswa=(Mahasiswa)sesi.getAttribute("mahasiswa");
                long matakuliah=Long.parseLong(request.getParameter("matakuliah"));
                String status=request.getParameter("status");
                String nilai=request.getParameter("nilai");
                RegistrasiMatakuliah regmat=new RegistrasiMatakuliah();
                regmat.setMahasiswa(mahasiswa);
                regmat.setMatakuliah(new MatakuliahDAO().getMatakuliah(matakuliah));
                regmat.setStatus(status);
                regmat.setNilai(nilai);
                regmat.setJadwal(new JadwalDAO().getJadwal(1));
                output = Integer.toString(new ObjectDAO().insert(regmat));
            }
            else if(task.equals("UPDATE")){
                long id = Long.parseLong(request.getParameter("id"));
                String nilai = request.getParameter("nilai");
                String status = request.getParameter("status");

                RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(id);
                reg.setNilai(nilai);
                reg.setStatus(status);
                output = Integer.toString(new ObjectDAO().update(reg));
            }
            else if(task.equals("DELETE")){
                long id = Long.parseLong(request.getParameter("id"));

                RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(id);
                output = Integer.toString(new ObjectDAO().delete(reg));
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(NilaiJSON.class.getName()).log(Level.SEVERE, null, ex);
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
