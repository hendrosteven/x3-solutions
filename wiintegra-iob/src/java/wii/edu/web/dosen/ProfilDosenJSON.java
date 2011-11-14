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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.DistrikDAO;
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Distrik;

/**
 *
 * @author Retha@wii
 */
public class ProfilDosenJSON extends HttpServlet {
   
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
            String task = request.getParameter("task");
            //HttpSession sesi = request.getSession();
            Dosen dos=(Dosen)sesi.getAttribute("currentDosen");

            if(task.equals("LISTING")){
                JSONObject root = new JSONObject();
                JSONArray dosen = new JSONArray();
                Distrik prov = dos.getDistrik();

                JSONObject jsonUser = new JSONObject();
                jsonUser.put( "id", dos.getId() );
                jsonUser.put( "userName", dos.getNomorPegawai() );
                jsonUser.put( "password", dos.getPassword() );
                jsonUser.put( "nama", dos.getNama() );
                jsonUser.put( "alamat", dos.getJalan() );
                jsonUser.put( "subDistrik", dos.getSubDistrik() );
                jsonUser.put( "distrik", prov.getId() );
                jsonUser.put( "kodepos", dos.getKodePos() );
                jsonUser.put( "telpon", dos.getTelepon() );
                jsonUser.put( "handphone", dos.getHandphone() );
                jsonUser.put( "email", dos.getEmail() );

                dosen.add( jsonUser );
                root.put( "daftarDosen", dosen );
                output = root.toString();
            }else if(task.equals("UPDATE")){
                String username = request.getParameter("userName");
                String password = request.getParameter("password");
                String nama = request.getParameter("nama");
                String alamat = request.getParameter("alamat");
                String subDistrik = request.getParameter("subDistrik");
                Long distrik = Long.parseLong(request.getParameter("distrik"));
                String kodepos = request.getParameter("kodepos");
                String telpon = request.getParameter("telpon");
                String handphone = request.getParameter("handphone");
                String email = request.getParameter("email");

                Dosen dosen = dos;
                dosen.setNomorPegawai(username);
                dosen.setPassword(password);
                dosen.setNama(nama);
                dosen.setJalan(alamat);
                dosen.setSubDistrik(subDistrik);

                Distrik prov = new DistrikDAO().getDistrik(distrik);
                dosen.setDistrik(prov);

                dosen.setKodePos(kodepos);
                dosen.setTelepon(telpon);
                dosen.setHandphone(handphone);
                dosen.setEmail(email);

                output = Integer.toString(new ObjectDAO().update(dos));
            }
            else if(task.equals("DISTRIK"))
                output = new DistrikDAO().getAllDistrikJSONObject().toString();

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(ProfilDosenJSON.class.getName()).log(Level.SEVERE, null, ex);
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
