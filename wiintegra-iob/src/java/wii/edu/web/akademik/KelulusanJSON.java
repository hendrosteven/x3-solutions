/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import wii.edu.core.dao.KelulusanDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.model.DataKelulusan;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class KelulusanJSON extends HttpServlet {
   
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

            if(task.equals("FAKULTAS"))
                output = new FakultasDAO().getAllFakultasJSONObject().toString();
            else if(task.equals("PROGDI")){
                String strFak = request.getParameter("fakultas");
                if(strFak == null || strFak.equals("")){
                    output = new ProgramStudiDAO().getAllProgramStudiMinusJSONObject().toString();
                }
                else{
                    long idFakultas = Long.parseLong(strFak);

                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new ProgramStudiDAO().getAllProgramStudiJSONObject(fak).toString();
                }
            }
            else if(task.equals("LISTING_LIMIT")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                   (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    output = new KelulusanDAO().getAllDataKelulusanJSONObject(start, limit).toString();
                }
                else if(!(request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                         (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new KelulusanDAO().getAllDataKelulusanJSONObject(fak, start, limit).toString();
                }
                else if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                        !(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    Fakultas fak = prog.getFakultas();
                    output = new KelulusanDAO().getAllDataKelulusanJSONObject(fak, prog, start, limit).toString();
                }
                else{
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    output = new KelulusanDAO().getAllDataKelulusanJSONObject(fak, prog, start, limit).toString();
                }
            }
            else if(task.equals("LISTING_BLM_LULUS")){
                output = new MahasiswaDAO().getAllMahasiswaBelumLulusJSONObject().toString();
            }
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa((Long)array.get(a));
                    DataKelulusan dataKelulusan = mahasiswa.getDataKelulusan();
                    mahasiswa.setDataKelulusan(null);
                    new ObjectDAO().update(mahasiswa);
                    new ObjectDAO().delete(dataKelulusan);
                }
                output = "1";
            }else if(task.equals("UPDATE")){
                long id = Long.parseLong(request.getParameter("id"));
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                System.out.println(request.getParameter("tglUjian"));
                Date tglUjian = format.parse(request.getParameter("tglUjian"));
                Date tglYudisium = format.parse(request.getParameter("tglYudisium"));
                String judulSkripsi = request.getParameter("judulSkripsi");
                String nilai = request.getParameter("nilai");
                //String predikat = request.getParameter("predikat");

                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(id);
                String predikat = new RegistrasiMatakuliahDAO().getPredikat(mahasiswa);

                DataKelulusan dataKelulusan = mahasiswa.getDataKelulusan();
                dataKelulusan.setTglUjian(tglUjian);
                dataKelulusan.setTglYudisium(tglYudisium);
                dataKelulusan.setJudulSkripsi(judulSkripsi);
                dataKelulusan.setNilai(nilai);
                dataKelulusan.setPredikat(predikat);

                output = Integer.toString(new ObjectDAO().update(mahasiswa));
            }else if(task.equals("CREATE")){
                long id = Long.parseLong(request.getParameter("id"));
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                System.out.println(request.getParameter("tglUjian"));
                Date tglUjian = format.parse(request.getParameter("tglUjian"));
                Date tglYudisium = format.parse(request.getParameter("tglYudisium"));
                String judulSkripsi = request.getParameter("judulSkripsi");
                String nilai = request.getParameter("nilai");
                //String predikat = request.getParameter("predikat");

                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(id);
                String predikat = new RegistrasiMatakuliahDAO().getPredikat(mahasiswa);

                DataKelulusan dataKelulusan = new DataKelulusan();
                dataKelulusan.setTglUjian(tglUjian);
                dataKelulusan.setTglYudisium(tglYudisium);
                dataKelulusan.setJudulSkripsi(judulSkripsi);
                dataKelulusan.setNilai(nilai);
                dataKelulusan.setPredikat(predikat);
                //new ObjectDAO().insert(dataKelulusan);
                mahasiswa.setDataKelulusan(dataKelulusan);

                output = Integer.toString(new ObjectDAO().update(mahasiswa));
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(KelulusanJSON.class.getName()).log(Level.SEVERE, null, ex);
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
