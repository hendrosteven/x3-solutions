/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
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
import wii.edu.core.dao.FakultasDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class ProgdiJSON extends HttpServlet {
   
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
                    if(user.getLevel() != 1){
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

            if(task.equals("LISTING")){
                String strFak = request.getParameter("idFakultas");
                if(strFak == null || strFak.equals("")){
                    output = new ProgramStudiDAO().getAllProgramStudiMinusJSONObject().toString();
                }
                else{
                    long idFakultas = Long.parseLong(strFak);

                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new ProgramStudiDAO().getAllProgramStudiJSONObject(fak).toString();
                }
            }
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                int ind = 0;
                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    ProgramStudi progdi = new ProgramStudiDAO().getProgramStudi((Long)array.get(a));
                    List listMhs = new MahasiswaDAO().getAllMahasiswa(progdi);
                    if((listMhs == null || listMhs.size()<=0) && (Long)array.get(a) != 1){
                        new ObjectDAO().delete(progdi);
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
                String kode = request.getParameter("kode");
                ProgramStudiDAO dao = new ProgramStudiDAO();

                if(dao.kodeIsUsed(kode) && dao.getProgramStudi(kode).getId() != id){
                    output = "-1";
                }
                else{
                String nama = request.getParameter("nama");
                long fakultas = Long.parseLong(request.getParameter("fakultas"));

                Fakultas fak = new FakultasDAO().getFakultas(fakultas);
                ProgramStudi progdi = new ProgramStudi();
                progdi.setId(id);
                progdi.setKode(kode);
                progdi.setNama(nama);
                progdi.setFakultas(fak);

                output = Integer.toString(new ObjectDAO().update(progdi));
                }
            }else if(task.equals("CREATE")){
                String kode = request.getParameter("kode");

                if(new ProgramStudiDAO().kodeIsUsed(kode)){
                    output = "-1";
                }
                else{
                    String nama = request.getParameter("nama");
                    long fakultas = Long.parseLong(request.getParameter("fakultas"));

                    Fakultas fak = new FakultasDAO().getFakultas(fakultas);
                    ProgramStudi progdi = new ProgramStudi();
                    progdi.setKode(kode);
                    progdi.setNama(nama);
                    progdi.setFakultas(fak);

                    output = Integer.toString(new ObjectDAO().insert(progdi));
                }
            }
            else if(task.equals("FAKULTAS"))
                output = new FakultasDAO().getAllFakultasJSONObject().toString();

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(ProgdiJSON.class.getName()).log(Level.SEVERE, null, ex);
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
