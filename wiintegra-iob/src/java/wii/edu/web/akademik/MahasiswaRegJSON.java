/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import wii.edu.core.dao.FakultasDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.PembayaranDAO;
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.Pembayaran;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.Semester;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class MahasiswaRegJSON extends HttpServlet {
   
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

            if(task.equals("REGISTRASI")){
                String jsonIds = request.getParameter("ids");
                //long idSemester = Long.parseLong(request.getParameter("idSemester"));
                Semester semester = new SemesterDAO().getCurrentSemester();

                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                for(int a=0;a<array.size();a++){
                    Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa((Long)array.get(a));

                    if(new PembayaranDAO().isLunas(mahasiswa, semester)){
                        output = "0";
                    }
                    else{
                        Pembayaran bayar = new Pembayaran();
                        bayar.setMahasiswa(mahasiswa);
                        bayar.setSemester(semester);
                        bayar.setTanggal(new Date());

                        output = Integer.toString(new ObjectDAO().insert(bayar));
                    }
                }
            }
            else if(task.equals("LISTINGREG_LIMIT")){
                Semester semester = new SemesterDAO().getCurrentSemester();
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                   (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    output = new PembayaranDAO().getAllMahasiswaTakTerregistrasiJSONObject(semester, start, limit).toString();
                }
                else if(!(request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                         (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new PembayaranDAO().getAllMahasiswaTakTerregistrasiJSONObject(semester, fak, start, limit).toString();
                }
                else if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                        !(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    Fakultas fak = prog.getFakultas();
                    output = new PembayaranDAO().getAllMahasiswaTakTerregistrasiJSONObject(semester, fak, prog, start, limit).toString();
                }
                else{
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    output = new PembayaranDAO().getAllMahasiswaTakTerregistrasiJSONObject(semester, fak, prog, start, limit).toString();
                }
            }
            else if(task.equals("FAKULTAS"))
                output = new FakultasDAO().getAllFakultasJSONObject().toString();
            else if(task.equals("PROGDI")){
                String strFak = request.getParameter("fakultas");
                System.out.println("strFak : " + strFak);
                if(strFak == null || strFak.equals("")){
                    output = new ProgramStudiDAO().getAllProgramStudiMinusJSONObject().toString();
                }
                else{
                    long idFakultas = Long.parseLong(strFak);

                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new ProgramStudiDAO().getAllProgramStudiJSONObject(fak).toString();
                }
            }
            else if(task.equals("CURRENT")){
                //output = new SemesterDAO().getCurrentSemesterJSONObject().toString();
                Semester semester = new SemesterDAO().getCurrentSemester();
                output = "Semester " + semester.getNama() + " Tahun Ajaran " + semester.getTahunAjaran();
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(MahasiswaUnregJSON.class.getName()).log(Level.SEVERE, null, ex);
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
