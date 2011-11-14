/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.mahasiswa;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import wii.edu.core.dao.MahasiswaIP;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;

/**
 *
 * @author Retha@wii
 */
public class KstMahasiswaJSON extends HttpServlet {
   
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

            if(task.equals("CEK")){
                int valid=1;
                Date TanggalSekarang=new Date();
                Date TanggalBatas=new SimpleDateFormat("yyyy-MM-dd").parse(new SemesterDAO().getCurrentSemester().getBatasRegistrasi());
                GregorianCalendar calTanggalBatas = new GregorianCalendar();
                calTanggalBatas.set(TanggalBatas.getYear(), TanggalBatas.getMonth(), TanggalBatas.getDate(), 0, 0, 0);
                GregorianCalendar calTanggalSekarang = new GregorianCalendar();
                calTanggalSekarang.set(TanggalSekarang.getYear(), TanggalSekarang.getMonth(), TanggalSekarang.getDate(), 0, 0, 0);

                if (calTanggalSekarang.after(calTanggalBatas)) {
                    valid=0;
                }

                JSONObject json = new JSONObject();
                json.put("valid", valid);
                json.put("currentSemester", semester.getNama() + " " + semester.getTahunAjaran());
                json.put("noIndukMhs", mahasiswa.getNomorInduk());
                json.put("namaMhs", mahasiswa.getNama());
                output = json.toString();
            }
            else if(task.equals("LISTING")){
                output = dao.getMatakuliahMahasiswaJSONObject(mahasiswa, semester).toString();
            }
            else if(task.equals("TOTAL")){
                output = dao.getMatakuliahMahasiswaJSONObject(mahasiswa, semester).toString();

                double IPK=new MahasiswaIP().getIPK(mahasiswa);
                double IPS=new MahasiswaIP().getIPS(mahasiswa,new SemesterDAO().getSemesterSebelumnya());
                int maxBeban=new MahasiswaIP().getMaxBeban(IPK,IPS);

                JSONObject json = dao.getDataTotal();
                json.put("maxBeban", maxBeban);

                output = json.toString();
            }
            else if(task.equals("UPDATE")){
                long id = Long.parseLong(request.getParameter("id"));
                String status = request.getParameter("status");

                RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(id);
                reg.setStatus(status);
                output = Integer.toString(new ObjectDAO().update(reg));
            }
            else if(task.equals("DELETE")){
                Date TanggalSekarang=new Date();
                Date TanggalBatas=new SimpleDateFormat("yyyy-MM-dd").parse(semester.getBatasRegistrasi());
                GregorianCalendar calTanggalBatas = new GregorianCalendar();
                calTanggalBatas.set(TanggalBatas.getYear(), TanggalBatas.getMonth(), TanggalBatas.getDay(), 0, 0, 0);
                GregorianCalendar calTanggalSekarang = new GregorianCalendar();
                calTanggalSekarang.set(TanggalSekarang.getYear(), TanggalSekarang.getMonth(), TanggalSekarang.getDay(), 0, 0, 0);

                if (calTanggalSekarang.after(calTanggalBatas)) {
                    output = "-1";
                }
                else{
                    String jsonIds = request.getParameter("ids");
                    System.out.println(jsonIds);
                    Object ids = JSONValue.parse(jsonIds);
                    JSONArray array=(JSONArray)ids;

                    for(int a=0;a<array.size();a++){
                        System.out.println(array.get(a));
                        RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah((Long)array.get(a));
                        new ObjectDAO().delete(reg);
                    }
                    output = "1";
                }
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(KstMahasiswaJSON.class.getName()).log(Level.SEVERE, null, ex);
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
