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
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.RuangDAO;
import wii.edu.core.model.Ruang;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class RuangJSON extends HttpServlet {
   
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

            if(task.equals("LISTING"))
                output = new RuangDAO().getAllRuangJSONObject().toString();
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                int ind = 0;
                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    Ruang rng = new RuangDAO().getRuang((Long)array.get(a));
                    List listJadwal = new JadwalDAO().getJadwal(rng);
                    if(listJadwal == null || listJadwal.size()<=0){
                        new ObjectDAO().delete(rng);
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
                RuangDAO dao = new RuangDAO();

                if(dao.kodeIsUsed(kode) && dao.getRuang(kode).getId() != id){
                    output = "-1";
                }
                else{
                String nama = request.getParameter("nama");
                int kapasitas = Integer.parseInt(request.getParameter("kapasitas"));
                Ruang rng = new Ruang();
                rng.setId(id);
                rng.setKode(kode);
                rng.setNama(nama);
                rng.setKapasitas(kapasitas);
                output = Integer.toString(new ObjectDAO().update(rng));
                }
            }else if(task.equals("CREATE")){
                //Long id = Long.parseLong(request.getParameter("id"));
                String kode = request.getParameter("kode");

                if(new RuangDAO().kodeIsUsed(kode)){
                    output = "-1";
                }
                else{
                    String nama = request.getParameter("nama");
                    int kapasitas = Integer.parseInt(request.getParameter("kapasitas"));
                    Ruang rng = new Ruang();
                    //rng.setId(id);
                    rng.setKode(kode);
                    rng.setNama(nama);
                    rng.setKapasitas(kapasitas);

                    output = Integer.toString(new ObjectDAO().insert(rng));
                }
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            System.out.println("masuk catch ini g?");
            Logger.getLogger(RuangJSON.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex.getMessage());
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
