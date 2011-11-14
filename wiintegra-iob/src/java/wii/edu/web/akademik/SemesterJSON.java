/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.PembayaranDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Semester;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class SemesterJSON extends HttpServlet {
   
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
            String task = request.getParameter("task");

            if(task.equals("LISTING_LIMIT")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                output = new SemesterDAO().getAllSemesterJSONObject(start, limit).toString();
            }
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                int ind = 0;
                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    Semester sem = new SemesterDAO().getSemester((Long)array.get(a));
                    List listBio = new MahasiswaDAO().getAllBiodata(sem);
                    List listJadwal = new JadwalDAO().getJadwal(sem);
                    List listMhs = new PembayaranDAO().getSemuaMahasiswaTerregistrasi(sem);
                    if((listBio == null || listBio.size()<=0) && (listJadwal == null || listJadwal.size()<=0) && (listMhs == null || listMhs.size()<=0)){
                        new ObjectDAO().delete(sem);
                        ind++;
                    }
                }

                if(ind == 0){
                    output = "0";
                }
                else if(ind == array.size()){
                    output = "1";
                }
                else if(ind < array.size()){
                    output = "-1";
                }
            }else if(task.equals("UPDATE")){
                Long id = Long.parseLong(request.getParameter("id"));
                int nama = Integer.parseInt(request.getParameter("nama"));
                String tahunAjaran = request.getParameter("tahunAjaran");
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Date tgl = format.parse(request.getParameter("batas"));
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                String batas = format2.format(tgl);
                //String batas = request.getParameter("batas");
                System.out.println("update : " + batas);
                Semester sem = new Semester();
                sem.setId(id);
                sem.setNama(nama);
                sem.setTahunAjaran(tahunAjaran);
                sem.setBatasRegistrasi(batas);
                output = Integer.toString(new ObjectDAO().update(sem));
            }else if(task.equals("CREATE")){
                //Long id = Long.parseLong(request.getParameter("id"));
                int nama = Integer.parseInt(request.getParameter("nama"));
                String tahunAjaran = request.getParameter("tahunAjaran");
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Date tgl = format.parse(request.getParameter("batas"));
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                String batas = format2.format(tgl);
                //String batas = request.getParameter("batas");
                System.out.println("create : " + batas);
                Semester sem = new Semester();
                //sem.setId(id);
                sem.setNama(nama);
                sem.setTahunAjaran(tahunAjaran);
                sem.setBatasRegistrasi(batas);
                output = Integer.toString(new ObjectDAO().insert(sem));
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(SemesterJSON.class.getName()).log(Level.SEVERE, null, ex);
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
